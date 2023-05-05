package mzc.app.adapter.sql;

import com.github.vertical_blank.sqlformatter.SqlFormatter;
import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.Table;
import mzc.app.adapter.base.IBasicAdapter;
import mzc.app.model.BaseModel;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class ModelAdapter<T extends BaseModel> implements IBasicAdapter<T> {
    protected final @NotNull HikariDataSource ds;

    public ModelAdapter(@NotNull HikariDataSource ds) {
        this.ds = ds;
    }

    @Override
    public T getById(long id) {
        var query = SqlFormatter.format("SELECT * FROM ? WHERE id = ?",
                FormatConfig.builder().params(Arrays.asList(getTableName(), id)).build());
        try (var con = ds.getConnection(); var stat = con.prepareStatement(query); var rs = stat.executeQuery()) {
            if (rs.next()) {
                return deserializeResult(rs);
            }
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void persist(@NotNull T model) {
        try {
            var fields = getFields(false);
            if (model.getId() == 0) {
                List<? super Object> params = new ArrayList<>();
                params.add(getTableName());
                var fieldsString = "(" + fields.stream().map(Field::getName).collect(Collectors.joining(", ")) + ")";
                params.add(fieldsString);
                for (var field : fields) {
                    field.setAccessible(true);
                    params.add(sanitizeField(field, model));
                }
                var valuesString = "(" + fields.stream().map(f -> "?").collect(Collectors.joining(", ")) + ")";
                var query = SqlFormatter.format("INSERT INTO ? ? VALUES " + valuesString, params);
                try (var con = ds.getConnection(); var stat = con.prepareStatement(query)) {
                    stat.executeUpdate();
                }
                try (var con = ds.getConnection(); var stat = con.prepareStatement("SELECT LAST_INSERT_ID()")) {
                    var res = stat.executeQuery();
                    if (res.next()) {
                        model.setId(res.getLong(1));
                        res.close();
                    } else {
                        res.close();
                        throw new SQLException("Cannot get last insert id");
                    }
                }
            } else {
                List<? super Object> params = new ArrayList<>();
                params.add(getTableName());
                for (var field : fields) {
                    field.setAccessible(true);
                    params.add(field.getName());
                    params.add(" = ");
                    params.add(sanitizeField(field, model));
                }
                var query = SqlFormatter.format("UPDATE ? SET ? WHERE id = ?",
                        FormatConfig.builder().params(params).build());
                try (var con = ds.getConnection(); var stat = con.prepareStatement(query)) {
                    stat.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(long id) {
        var query = SqlFormatter.format("DELETE FROM ? WHERE id = ?",
                FormatConfig.builder().params(Arrays.asList(getTableName(), id)).build());
        try (var con = ds.getConnection(); var stat = con.prepareStatement(query)) {
            stat.executeUpdate();
        } catch (SQLException ignored) {
        }
    }

    @Override
    public @NotNull Set<T> getInIds(@NotNull Set<Long> ids) {
        var query = SqlFormatter.format("SELECT * FROM ? WHERE id IN ?",
                FormatConfig.builder().params(List.of(getTableName(), String.format("(%s)",
                        ids.stream().map(String::valueOf).collect(Collectors.joining(", "))
                ))).build());
        try (var con = ds.getConnection(); var stat = con.prepareStatement(query); var rs = stat.executeQuery()) {
            return deserializeResults(rs);
        } catch (SQLException e) {
            return new LinkedHashSet<>();
        }
    }

    @Override
    public void delete(@NotNull T model) {
        deleteById(model.getId());
    }

    @Override
    public @NotNull Set<T> getAll() {
        var query = SqlFormatter.format("SELECT * FROM ?", Collections.singletonList(getTableName()));
        try(var con = ds.getConnection(); var stat = con.prepareStatement(query); var rs = stat.executeQuery()) {
            return deserializeResults(rs);
        } catch (SQLException e) {
            return new LinkedHashSet<>();
        }
    }


    abstract protected Class<T> getType();

    public String getTableName() {
        return this.getType().getAnnotation(Table.class).name();
    }

    @SuppressWarnings("unchecked")
    public T deserializeResult(ResultSet res) {
        try {
            var model = getType().getDeclaredConstructor().newInstance();
            var fields = getFields(true);
            for (var field : fields) {
                if (!Modifier.isTransient(field.getModifiers())) {
                    field.setAccessible(true);
                    if (field.getType().isEnum()) {
                        field.set(model, getEnum((Class<? extends Enum<?>>) field.getType(), res.getInt(field.getName())));
                    } else {
                        field.set(model, res.getObject(field.getName()));
                    }
                }
            }
            return model;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Set<T> deserializeResults(ResultSet res) {
        Set<T> models = new LinkedHashSet<>();
        try {
            while (res.next()) {
                models.add(deserializeResult(res));
            }
            return models;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String sanitizeString(String string) {
        return string.replace("\"", "\\\"");
    }

    public Object sanitizeField(Field field, T model) {
        try {
            var value = field.get(model);
            if (value instanceof String) {
                value = "\"" + sanitizeString((String) value) + "\"";
            } else if (value.getClass().isEnum()) {
                value = ((Enum<?>) value).ordinal();
            }
            return value;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <U extends Enum<?>> U getEnum(Class<U> enumClass, int ordinal) {
        return enumClass.getEnumConstants()[ordinal];
    }

    public List<Field> getFields(boolean includeId) {
        var res = Arrays.stream(this.getType().getDeclaredFields()).filter(f -> !Modifier.isTransient(f.getModifiers())).collect(Collectors.toList());
        if (includeId) {
            try {
                res.add(0, this.getType().getSuperclass().getDeclaredField("id"));
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
        return res;
    }
}
