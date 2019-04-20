package com.yogesh.lucene;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.LockObtainFailedException;

public class UploadServlet extends HttpServlet{
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
    throws ServletException,IOException
   {
       PrintWriter out = res.getWriter();
       res.setContentType("text/html");
       out.println("this is First servlet Example ");
       String contentType = req.getContentType();
       if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {
       DataInputStream in = new DataInputStream(req.getInputStream());
       int formDataLength = req.getContentLength();
       byte dataBytes[] = new byte[formDataLength];
       int byteRead = 0;
       int totalBytesRead = 0;
       while (totalBytesRead < formDataLength) {
       byteRead = in.read(dataBytes, totalBytesRead,formDataLength);
       totalBytesRead += byteRead;
       }
       String file = new String(dataBytes);
       String saveFile = file.substring(file.indexOf("filename=\"") + 10);
       saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
       saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1,saveFile.indexOf("\""));
       int lastIndex = contentType.lastIndexOf("=");
       String boundary = contentType.substring(lastIndex + 1,contentType.length());
       int pos;
       pos = file.indexOf("filename=\"");
       pos = file.indexOf("\n", pos) + 1;
       pos = file.indexOf("\n", pos) + 1;
       pos = file.indexOf("\n", pos) + 1;
       int boundaryLocation = file.indexOf(boundary, pos) - 4;
       int startPos = ((file.substring(0, pos)).getBytes()).length;
       int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
       File f = new File("C:\\Store\\"+saveFile);
       FileOutputStream fileOut = new FileOutputStream(f);
       fileOut.write(dataBytes, startPos, (endPos - startPos));
       fileOut.flush();
       fileOut.close();
       createIndex();
       RequestDispatcher rd=req.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
       rd.forward(req, res);
       
   }

   }
	public static void createIndex() throws CorruptIndexException, LockObtainFailedException, IOException {
		Analyzer analyzer = new SimpleAnalyzer();
		boolean recreateIndexIfExists = true;
		IndexWriter indexWriter = new IndexWriter("C:\\Store1", analyzer, recreateIndexIfExists);
		File dir = new File("C:\\Store");
		File[] files = dir.listFiles();
		for (File file : files) {
			Document document = new Document();

			String path = file.getCanonicalPath();
			document.add(new Field("path", path, Field.Store.YES, Field.Index.UN_TOKENIZED));
			String nam=file.getName();
			document.add(new Field("filename", nam, Field.Store.YES, Field.Index.UN_TOKENIZED));
			Reader reader = new FileReader(file);
			document.add(new Field("contents", reader));

			indexWriter.addDocument(document);
		}
		indexWriter.optimize();
		indexWriter.close();
	}


	
   }
