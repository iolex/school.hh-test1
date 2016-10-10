package com.company;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by iolex on 08.10.2016.
 */
public class AdditionElementRelated {

    private String index;
    private Map<String, SoftReference<AdditionElement>> realData;
    private SoftReference<AdditionElementRelated> referenceData;
    public AdditionElementRelated(String index) {
        this.index = index;
        this.realData = new HashMap<>();
    }

    public String getIndex() {
        return this.index;
    }

    public void setRealData(Map<String, SoftReference<AdditionElement>> realData) {
        this.realData = realData;
        this.referenceData = null;
    }

    public void setReferenceData(SoftReference<AdditionElementRelated> referenceData) {
        this.realData = null;
        this.referenceData = referenceData;
    }

    // @TODO: NullPointerException
    public Map<String, SoftReference<AdditionElement>> getData() {
        if (this.realData == null) {
            if (this.referenceData == null) {
                this.realData = new HashMap<>();
            }
            else {
                return this.referenceData.get().getData();
            }
        }
        return this.realData;
    }



    // @TODO: NullPointerException
    public String toString() {
        return
                "AdditionElementRelated {index: " + this.index
                        + ", real: " + (this.realData == null ? "0" : this.realData.size() + "[" + this.realData.keySet() + "]")
                        + ", reference: " + (this.referenceData == null ? "0" : this.referenceData.get().getData().size())
                        + "}";
    }

}
