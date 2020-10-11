package com.kumar.akshay.tasktimer;

import android.util.Log;

import java.io.Serializable;

class Tasks implements Serializable {
    private static final long seriaVersion = 20200419L;
    private long m_Id;
    private final String name;
    private final String descriptiopn;
    private final int sortorder;

    public Tasks(long Id,String name, String Description, int sortorder) {
        this.m_Id = Id;
        this.name=name;
        this.descriptiopn = Description;
        this.sortorder=sortorder;
    }

    public long getId() {
        return m_Id;
    }

    public String getName() {
        return name;
    }

    public String getDescriptiopn() {
        return descriptiopn;
    }

    public int getSortorder() {
        return sortorder;
    }

    public void Id(long Id) {
        this.m_Id = Id;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "m_Id=" + m_Id +
                ", name='" + name + '\'' +
                ", descriptiopn='" + descriptiopn + '\'' +
                ", sortorder=" + sortorder +
                '}';
    }
}
