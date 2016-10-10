package com.company;

/**
 * Created by iolex on 08.10.2016.
 */
public class Perimeter {

    private Integer index;
    private Integer elementsCount;
    private Integer lastGullyCount;
    public Perimeter(Integer index, Integer elementsCount) {
        this.index = index;
        this.elementsCount = elementsCount;
        this.lastGullyCount = 0;
    }

    public Integer getIndex() {
        return this.index;
    }

    public Integer getElementsCount() {
        return this.elementsCount;
    }

    public Integer getLastGullyCount() {
        return this.lastGullyCount;
    }

    public void setLastGullyCount(Integer lastGullyCount) {
        this.lastGullyCount = lastGullyCount;
    }



    public String toString() {
        return
                "Perimeter {index: " + this.index
                    + ", elementsCount: " + this.elementsCount.toString()
                    + ", lastGullyCount: " + this.lastGullyCount
                    + "}";
    }

}
