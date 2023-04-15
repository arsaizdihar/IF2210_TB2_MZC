package mzc.app.adapter.base;

import lombok.NonNull;

public interface IMainAdapter {

    @NonNull IMemberAdapter getMember();

    @NonNull IBillAdapter getBill();

    void close();
}
