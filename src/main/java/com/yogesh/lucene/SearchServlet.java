package com.yogesh.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hit;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class SearchServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req,HttpServletResponse res)
    throws ServletException,IOException
   {
		int noo=0;
		HttpSession ss=req.getSession();
		if(req.getParameter("pagenumber")!=null)
		{
			 noo= 1;
			 ss.setAttribute("count",null);
		}
		else{
			System.out.println("getparam null");
		}
		
		try {
			
			
			System.out.println("output  "+req.getParameter("field"));
			String no="";
			String msg="";
			/*if(req.getParameter("pagenumber")!=null )
			{
				no=req.getParameter("pagenumber");
				
			}
			else
			{
				no="1";
				
			}*/
			if(req.getParameter("field")!=null)
			{
				msg=req.getParameter("field");
				ss.setAttribute("message",msg);
			}
			System.out.println("before check");
			if(ss.getAttribute("count")!=null)
			{
				noo=(Integer)(ss.getAttribute("count"));
				noo++;
				ss.setAttribute("count",noo);
			}
			else
			{
				ss.setAttribute("count",noo);
			}
			System.out.println("Page numbersssssssssss "+noo);
			
			List value=searchIndex(msg,Integer.valueOf(noo));
			//System.out.println("Size is "+value);
			SearchResult srs=(SearchResult) value.get(0);
			System.out.println("total hitssss :::"+srs.getSize());
			ss.setAttribute("totalhits",srs.getSize());
			ss.setAttribute("output",value);
			RequestDispatcher rd=req.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
		       rd.forward(req, res);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
   }
	
	public static List searchIndex(String searchString,int pageNumber) throws IOException, ParseException {
		
		
		String path = "No Match";
		Hit hit = null;
	 int maxNumberOfResults;

		System.out.println("Searching for '" + searchString + "'");
		Directory directory = FSDirectory.getDirectory("C:\\Store1");
		IndexReader indexReader = IndexReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);

		Analyzer analyzer = new SimpleAnalyzer();
		QueryParser queryParser = new QueryParser("contents", analyzer);
		Query query = queryParser.parse(searchString);
		Hits hits = indexSearcher.search(query);
		System.out.println("Number of hits: " + hits.length());
		
		List ls= new ArrayList();
		Iterator<Hit> it = hits.iterator();
		while (it.hasNext()) {
			 hit = it.next();
			Document document = hit.getDocument();
			path = document.get("path");
			
			System.out.println("Hit: " + path);
		}
		TopDocs hitss = indexSearcher.search(query,null,hits.length());
		System.out.println("value are ::::: "+hitss.scoreDocs);
	//	Hits hits=(Hits)indexSearcher.search(query,null,10);
		DataLocation arrayLocation = new Paginator().calculateArrayLocation(hitss.scoreDocs.length, pageNumber, 2);
		System.out.println("pagenumber:::::"+pageNumber);
		System.out.println("arrayLocation.getStart()"+arrayLocation.getStart());
		System.out.println("arrayLocation.getEnd()"+arrayLocation.getEnd());
		
        for (int i = arrayLocation.getStart() - 1; i < arrayLocation.getEnd(); i++) {
                SearchResult sr = new SearchResult();
sr.setSize(hits.length());
                int docId = hitss.scoreDocs[i].doc;
                System.out.println("doc id :::::: "+docId);
                //load the document
                Document doc = indexSearcher.doc(docId);
                String filename = doc.get("filename");
                System.out.println("name detials "+filename);
                String contents =  doc.get(searchString);
                String pat=doc.get("path");
                System.out.println("pattttt ::: "+pat);
                sr.setSetpath(pat);
                sr.setFilename(filename);
                ls.add(sr);

        }
		
		return ls;
		
		

	}
}
