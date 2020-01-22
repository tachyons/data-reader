package org.metastringfoundation.healthheatmap;

public class DataHeader {
    public String dataHeader;

    public DataHeader(String dataHeader) {
        this.dataHeader = dataHeader;
    }

    public static DataHeader dataHeader(String dataHeader) {
        return new DataHeader(dataHeader);
    }
}
