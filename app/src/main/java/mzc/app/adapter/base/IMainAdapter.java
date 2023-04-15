package mzc.app.adapter.base;

import lombok.NonNull;

public interface IMainAdapter {

    @NonNull
    public IMemberAdapter getMember();

    public void close();
}
