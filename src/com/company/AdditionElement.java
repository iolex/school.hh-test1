package com.company;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by iolex on 08.10.2016.
 */
public class AdditionElement {

    private String index;
    private Integer value;
    private Boolean gully;
    private SoftReference<AdditionElement> elementReference;
    private Map<String, SoftReference<AdditionElement>> border;
    private AdditionElementRelated related;
    private SoftReference<AdditionElementRelated> relatedReference;
    public AdditionElement(String index, Integer value, Boolean gully) {
        this.index = index;
        this.value = value;
        this.gully = gully;

        this.border = new HashMap<>();
        this.related = new AdditionElementRelated(this.index);
    }

    public String getIndex() {
        return this.index;
    }

    public Integer getValue() {
        return this.value;
    }

    public Boolean isGully() {
        return this.gully;
    }

    public void setGully(Boolean gully) {
        this.gully = gully;
    }

    public SoftReference<AdditionElement> getElementReference() {
        if (this.elementReference == null) {
            this.elementReference = new SoftReference<>(this);
        }
        return this.elementReference;
    }

    public void setBorder(String direction, SoftReference<AdditionElement> element) {
        if (this.border == null) {
            this.border = new HashMap<>();
        }
        border.put(direction, element);
    }

    public Map<String, SoftReference<AdditionElement>> getBorders() {
        if (this.border == null) {
            this.border = new HashMap<>();
        }
        return border;
    }

    public void setRelatedData(Map<String, SoftReference<AdditionElement>> related) {
        if (this.related == null) {
            this.related = new AdditionElementRelated(this.index);
        }
        this.related.setRealData(related);
    }

    public void setRelatedReference(SoftReference<AdditionElementRelated> related) {
        if (this.related == null) {
            this.related = new AdditionElementRelated(this.index);
        }
        this.related.setReferenceData(related);
    }

    public Map<String, SoftReference<AdditionElement>> getRelatedData() {
        if (this.related == null) {
            this.related = new AdditionElementRelated(this.index);
        }
        return this.related.getData();
    }

    public SoftReference<AdditionElementRelated> getRelatedReference() {
        if (this.relatedReference == null) {
            if (this.related == null) {
                this.related = new AdditionElementRelated(this.index);
            }
            this.relatedReference = new SoftReference<>(this.related);
        }
        return this.relatedReference;
    }

    public void incrementValue(Integer value) {
        this.value += value;
    }



    public String toString() {
        return
                "AdditionElement {index: " + this.getIndex()
                        + ", value: " + this.getValue()
                        + ", gully: " + this.isGully()
                        + ", rel: " + (this.related == null ? "-" : this.related.toString())
                        + "}";
    }

}
