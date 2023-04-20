package mzc.app.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class ViewModelContext {
    private @NotNull List<String> tabs = new ArrayList<>();
}
