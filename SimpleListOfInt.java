package com.app;

/**
 * This class is a simple list of primitive ints to save
 * space since the java List<Integer> uses significantly more memory
 */
class SimpleListOfInt {
    private int [] list;
    private int length;

    public SimpleListOfInt() {
        this.list = new int[2];
        this.length = 0;
    }


    /*method to copy and double the length of the array
    * containing the list.*/
    private void CopyAndDouble() {
        int[] tmp = new int[this.list.length * 2 ];
        for (int i = 0; i < this.length; ++i){
            tmp[i] = this.list[i];
        }
        this.list = tmp;
    }


    /*Adds a number to the list */
    public void Add(int num){
        if (this.list.length == length) {
            this.CopyAndDouble();
        }
        this.list[length] = num;
        ++this.length;
    }

    /*this returns the length of a simple list of int*/
    public int Length() {
        return this.length;
    }

    /*This gets the value at a specified index in the list*/
    public int Get(int index) {
        if(index >= this.length){
            throw new NullPointerException("Index out of bounds");
        }
        else {
            return list[index];
        }
    }


}
