package agh.bit.ideafactory.helpers;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadFile {


	  private CommonsMultipartFile fileData;
	 
	  public CommonsMultipartFile getFileData()
	  {
	    return fileData;
	  }
	 
	  public void setFileData(CommonsMultipartFile fileData)
	  {
	    this.fileData = fileData;
	  }
	
}
