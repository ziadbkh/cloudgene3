package cloudgene.mapred.wdl;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonClassDescription
public class WdlApp implements Comparable<WdlApp> {

	private String description = "";

	private String version;

	private String website;

	private String name;

	private String category = "Application";

	private String author;

	private String id;

	private String release;
	
	private String logo;

	private String submitButton = "Submit Job";

	private WdlWorkflow workflow;

	private Map<String, Object> properties;

	private String path;

	private String manifestFile;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public WdlWorkflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(WdlWorkflow workflow) {
		this.workflow = workflow;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setSubmitButton(String submitButton) {
		this.submitButton = submitButton;
	}

	public String getSubmitButton() {
		return submitButton;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public String getRelease() {
		return release;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public String getLogo() {
		return logo;
	}
	
	/* intern variables */

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setManifestFile(String manifestFile) {
		this.manifestFile = manifestFile;
	}

	public String getManifestFile() {
		return manifestFile;
	}

	@Override
	public int compareTo(WdlApp o) {
		// sort by type
		return getName().compareToIgnoreCase(o.getName());
	}

}
