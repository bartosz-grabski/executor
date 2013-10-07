package agh.bit.ideafactory.helpers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadForm {
	private List<MultipartFile> files = new ArrayList<MultipartFile>();

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public boolean isTestSetValid() {
		return !this.files.isEmpty() && this.files.size() % 2 == 0;
	}
}
