package com.fireflyingup.deerlet.spy;

public interface Spy {

    void before(Object target, Object[] args);

    void after(Object target, Object[] args);

    void around(Object target, Object[] args);

    void exception(Object target, Object[] args);

}
