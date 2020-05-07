/*
 *    Copyright 2020 Metastring Foundation
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.metastringfoundation.datareader.dataset.table;

import java.util.Locale;

public class TableCellReference {
    private int row = 0;
    private int column = 0;

    public TableCellReference(String reference) {
         parseReference(reference);
    }

    public TableCellReference(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public TableCellReference(){}

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    private void parseReference(String reference) {
        String textPart = reference.replaceAll("[^A-z]+", "");
        String numberPart = reference.replaceAll("[^0-9]+", "");

        if (numberPart.length() == 0) {
            this.row = Integer.MAX_VALUE;
        } else {
            this.row = getRowFromReference(numberPart);
        }

        if (textPart.length() == 0) {
            this.column = Integer.MAX_VALUE;
        } else {
            this.column = getColumnFromReference(textPart);
        }
    }

    private int getRowFromReference(String rowReference) {
        return Integer.parseInt(rowReference) - 1;
    }

    private int getColumnFromReference(String columnReference) {
        return convertColStringToIndex(columnReference);
    }

    /**
     * This function has been copied from Apache POI library (which is under Apache License)
     * https://github.com/apache/poi/blob/trunk/src/java/org/apache/poi/ss/util/CellReference.java
     * takes in a column reference portion of a CellRef and converts it from
     * ALPHA-26 number format to 0-based base 10.
     * 'A' -&gt; 0
     * 'Z' -&gt; 25
     * 'AA' -&gt; 26
     * 'IV' -&gt; 255
     * @return zero based column index
     */
    @SuppressWarnings("SpellCheckingInspection")
    private static int convertColStringToIndex(String ref) {
        char ABSOLUTE_REFERENCE_MARKER = '$';
        int retval=0;
        char[] refs = ref.toUpperCase(Locale.ROOT).toCharArray();
        for (int k=0; k<refs.length; k++) {
            char thechar = refs[k];
            if (thechar == ABSOLUTE_REFERENCE_MARKER) {
                if (k != 0) {
                    throw new IllegalArgumentException("Bad col ref format '" + ref + "'");
                }
                continue;
            }

            // Character is uppercase letter, find relative value to A
            retval = (retval * 26) + (thechar - 'A' + 1);
        }
        return retval-1;
    }

    /**
     * This function has been copied from Apache POI library (which is under Apache License)
     * https://github.com/apache/poi/blob/trunk/src/java/org/apache/poi/ss/util/CellReference.java
     * Takes in a 0-based base-10 column and returns a ALPHA-26
     *  representation.
     * eg {@code convertNumToColString(3)} returns {@code "D"}
     */
    public static String convertNumToColString(int col) {
        // Excel counts column A as the 1st column, we
        //  treat it as the 0th one
        int excelColNum = col + 1;

        StringBuilder colRef = new StringBuilder(2);
        int colRemain = excelColNum;

        while(colRemain > 0) {
            int thisPart = colRemain % 26;
            if(thisPart == 0) { thisPart = 26; }
            colRemain = (colRemain - thisPart) / 26;

            // The letter A is at 65
            char colChar = (char)(thisPart+64);
            colRef.insert(0, colChar);
        }

        return colRef.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        TableCellReference other = (TableCellReference) obj;
        return (other.getRow() == (this.getRow())
                && other.getColumn() == this.getColumn()
        );
    }

    @Override
    public String toString() {
        return "TableCellReference{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}
