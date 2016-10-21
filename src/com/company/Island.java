package com.company;
import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by iolex on 08.10.2016.
 */
public class Island {

    private Integer N;
    private Integer M;
    private List<List<Integer>> source;
    public Island(List<List<Integer>> source, Integer N, Integer M) {
        this.source = source;
        this.N = N;
        this.M = M;
    }

    public Integer calculate() {

        Integer result = 0;
        if (this.N < 3 || this.M < 3) {
            return result;
        }
        this.rollSource();

        // compute borders inside 1 perimeter
        // remember that sizes > 3
        for (int i = 1; i < this.iMax - 1; i++) {
            for (int j = 1; j < this.jMax - 1; j++) {
                this.additions.get(i).get(j).setBorder("top", this.additions.get(i - 1).get(j).getElementReference());
                this.additions.get(i).get(j).setBorder("left", this.additions.get(i).get(j - 1).getElementReference());
                this.additions.get(i).get(j).setBorder("right", this.additions.get(i).get(j + 1).getElementReference());
                this.additions.get(i).get(j).setBorder("bottom", this.additions.get(i + 1).get(j).getElementReference());
            }
        }

        // compute perimeters start from 1, ordered
        Integer elementsCount;
        List<Perimeter> perimeter = new ArrayList<>();
        for (int i = 1; i <= (int)((this.jMax - 1) / 2); i++) {
            elementsCount = (this.iMax + this.jMax - i * 4) * 2 - 4;
            elementsCount = elementsCount == 0 ? 1 : elementsCount;
            if (elementsCount > 0) {
                perimeter.add(new Perimeter(i, elementsCount));
            }
        }



        while (perimeter.size() > 0) {

            // build related fields, walk by perimeter
            for (int i = 0; i < perimeter.size(); i++) {
                this.walkAdditionsPerimeter(perimeter.get(i).getIndex(), "markRelated");
            }

            // mark gully, walk by perimeters
            boolean search = true;
            boolean lastCheck = true;
            int perimeterIterator = 0;
            while (search) {
                Integer currentPerimeterElementsCount = perimeter.get(perimeterIterator).getElementsCount();
                Integer currentPerimeterLastGullyCount = perimeter.get(perimeterIterator).getLastGullyCount();
                Integer markGully = this.walkAdditionsPerimeter(perimeter.get(perimeterIterator).getIndex(), "markGully");

                perimeter.get(perimeterIterator).setLastGullyCount(markGully);
                lastCheck = lastCheck && markGully.equals(currentPerimeterLastGullyCount);
                if (markGully == 0) {
                    if (lastCheck) {
                        search = false;
                    }
                    else {
                        lastCheck = true;
                        perimeterIterator = 0;
                    }
                }
                else if (markGully.equals(currentPerimeterElementsCount)) {
                    perimeter.remove(perimeterIterator);
                }
                else if (markGully.equals(currentPerimeterLastGullyCount)) {
                    perimeterIterator++;
                }

                if (perimeterIterator >= perimeter.size()) {
                    if (lastCheck) {
                        search = false;
                    }
                    else {
                        lastCheck = true;
                        perimeterIterator = 0;
                    }
                }

                if (perimeterIterator >= perimeter.size()) {
                    search = false;
                }
            }
            //this.drawAdditions();
            // may-be that's fin
            if (perimeter.size() == 0) {
                break;
            }

            // search minimum not gully, walk by perimeter
            this.minimumElements = new ArrayList<>();
            this.currentMinimum = null;
            this.currentMinimumBorder = null;
            for (int i = 0; i < perimeter.size(); i++) {
                this.walkAdditionsPerimeter(perimeter.get(i).getIndex(), "searchMinimum");
            }

            // @TODO: NullPointerException
            Integer water = this.currentMinimumBorder - this.currentMinimum;
            for (int i = 0; i < this.minimumElements.size(); i++) {
                this.minimumElements.get(i).get().incrementValue(water);
                result += water;
            }
            //System.out.println("\r\n+ " + water + "*" + this.minimumElements.size());

        }

        return result;
    }

    private void drawAdditions() {

        AdditionElement current;
        System.out.print("\r\n");
        for (int i = 0; i < this.iMax; i++) {
            for (int j = 0; j < this.jMax; j++) {
                current = this.additions.get(i).get(j);
                if (current.isGully()) {
                    System.out.print("[");
                }
                else {
                    System.out.print(" ");
                }
                System.out.print(current.getValue().toString());
                if (current.isGully()) {
                    System.out.print("]");
                }
                else {
                    System.out.print(" ");
                }
            }
            System.out.print("\r\n");
        }
    }

    public Integer drawElement(AdditionElement element) {

        System.out.print("> " + element.toString());
        System.out.print("\r\n");

        return 1;
    }



    private Integer iMax;
    private Integer jMax;
    private Map<Integer, Map<Integer, AdditionElement>> additions;
    private void rollSource() {

        int i = 0, j = 0;
        int _i = 0, _j = 0;

        this.additions = new HashMap<>();
        Map<Integer, AdditionElement> additionsLine;

        for (i = 0; i < this.N; i++) {
            for (j = 0; j < this.M; j++) {
                _i = (this.N >= this.M) ? i : j;
                _j = (this.N >= this.M) ? j : i;

                additionsLine = this.additions.containsKey(_i)
                        ? this.additions.get(_i)
                        : new HashMap<>();

                additionsLine.put(
                        _j,
                        new AdditionElement(
                                "(" + _i + "," + _j + ")",
                                this.source.get(i).get(j),
                                (i == 0 || j == 0 || i == (this.N - 1) || j == (this.M - 1))
                        )
                );
                this.additions.put(_i, additionsLine);
            }
        }

        this.iMax = (_i > 0) ? _i + 1 : 0;
        this.jMax = (_j > 0) ? _j + 1 : 0;
    }

    // @TODO: NullPointerException
    public Integer markRelated(AdditionElement element) {

        Map<String, SoftReference<AdditionElement>> newRelation = new HashMap<>();
        newRelation.put(element.getIndex(), element.getElementReference());
        newRelation.putAll(element.getRelatedData());

        //element.get().setRelatedData(newRelation);
        for (SoftReference<AdditionElement> borderElementReference : element.getBorders().values()) {
            if (borderElementReference.get().getValue().equals(element.getValue())) {
                newRelation.put(borderElementReference.get().getIndex(), borderElementReference);
                if (borderElementReference.get().getRelatedData().size() > 0) {
                    newRelation.putAll(borderElementReference.get().getRelatedData());
                }
            }
        }
        element.setRelatedData(newRelation);

        // @TODO: блеать, по ссылке или по значению..., зафига тогда я верчу SoftReference
        //newRelation.remove(element.getIndex());
        for (SoftReference<AdditionElement> newRelationElement : newRelation.values()) {
            if (!newRelationElement.get().getIndex().equals(element.getIndex())) {
                newRelationElement.get().setRelatedReference(element.getRelatedReference());
            }
        }

        return 1;
    }

    // @TODO: NullPointerException
    // as return - is element gully after check?
    public Integer markGully(AdditionElement element) {

        Integer result = 0;

        if (!element.isGully()) {
            for (SoftReference<AdditionElement> borderElementReference : element.getBorders().values()) {
                if (borderElementReference.get().isGully() && borderElementReference.get().getValue() <= element.getValue()) {
                    for (SoftReference<AdditionElement> relatedElementReference : element.getRelatedData().values()) {
                        relatedElementReference.get().setGully(true);
                    }
                }
            }
        }
        if (element.isGully()) {
            result = 1;
        }

        return result;
    }

    private List<SoftReference<AdditionElement>> minimumElements;
    private Integer currentMinimum;
    private Integer currentMinimumBorder;
    // @TODO: NullPointerException
    public Integer searchMinimum(AdditionElement element)
    {

        if (element.isGully()) {
            return 0;
        }
        if (this.currentMinimum == null || this.currentMinimum > element.getValue()) {
            this.currentMinimum = element.getValue();
            this.minimumElements = new ArrayList<>();
            minimumElements.add(element.getElementReference());
            this.currentMinimumBorder = null;
            for (SoftReference<AdditionElement> borderElementReference : element.getBorders().values()) {
                if (
                        borderElementReference.get().getValue() > this.currentMinimum
                                && (this.currentMinimumBorder == null || this.currentMinimumBorder > borderElementReference.get().getValue())
                        ) {
                    this.currentMinimumBorder = borderElementReference.get().getValue();
                }
            }
        }
        else if (this.currentMinimum.equals(element.getValue())) {
            this.minimumElements.add(element.getElementReference());
            for (SoftReference<AdditionElement> borderElementReference : element.getBorders().values()) {
                if (
                        borderElementReference.get().getValue() > this.currentMinimum
                                && (this.currentMinimumBorder == null || this.currentMinimumBorder > borderElementReference.get().getValue())
                        ) {
                    this.currentMinimumBorder = borderElementReference.get().getValue();
                }
            }
        }

        return 1;
    }



    private Integer walkAdditionsPerimeter(Integer size, String callback) {

        Integer callCount = 0;
        Method method;
        try {
            method = this.getClass().getMethod(callback, AdditionElement.class);
        } catch (Exception e) { return callCount; }

        int iTop = size, iBottom = this.iMax - size - 1;
        boolean iBoth = (iTop != iBottom);
        for (int j = size; j < this.jMax - size; j++) {
            try {
                Object value = method.invoke(this, this.additions.get(iTop).get(j));
                callCount += (int)value;
            } catch (Exception e) { return callCount; }
            if (iBoth) {
                try {
                    Object value = method.invoke(this, this.additions.get(iBottom).get(j));
                    callCount += (int)value;
                } catch (Exception e) { return callCount; }
            }
        }

        int jLeft = size, jRight = this.jMax - size - 1;
        boolean jBoth = (jLeft != jRight);
        for (int i = iTop + 1; i < iBottom; i++) {
            try {
                Object value = method.invoke(this, this.additions.get(i).get(jLeft));
                callCount += (int)value;
            } catch (Exception e) { return callCount; }
            if (jBoth) {
                try {
                    Object value = method.invoke(this, this.additions.get(i).get(jRight));
                    callCount += (int)value;
                } catch (Exception e) { return callCount; }
            }
        }

        return callCount;
    }



    public String toString() {
        return this.source.toString() + "\r\nN=" + this.N + "\r\nM=" + this.M;
    }

}
