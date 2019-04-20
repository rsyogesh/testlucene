package com.yogesh.lucene;

public class Paginator {
  
    public DataLocation calculateArrayLocation(int totalHits, int pageNumber, int pageSize) {
            DataLocation al = new DataLocation();

            if (totalHits < 1 || pageNumber < 1 || pageSize < 1) {
                    al.setStart(0);
                    al.setEnd(0);
                    return al;
            }

            int start = 1 + (pageNumber - 1) * pageSize;
            /*System.out.println("paginator ::::::start"+start);
            System.out.println("paginator page:::::"+pageNumber);
            System.out.println("paginator Size:::::"+pageSize);*/
            int cal=pageNumber * pageSize;
           /* System.out.println("calucualtere :::::::::"+cal);
            System.out.println("totalHits :::::::::::"+totalHits);*/
            int end = Math.min(cal,totalHits);
           // System.out.println("paginator :::::: end"+end);
            if (start > end) {
                   // start = Math.max(1, end - pageSize);
            	start=end;
            }

            al.setStart(start);
            al.setEnd(end);
            return al;
    }
}

