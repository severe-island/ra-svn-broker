package org.repoaggr.svnbrk.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.repoaggr.svnbrk.model.CommitDataFiles;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CommitData
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-12-09T09:35:53.477Z")

public class CommitData   {
  @JsonProperty("committed_at")
  private BigDecimal committedAt = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("committer")
  private String committer = null;

  @JsonProperty("positiveDelta")
  private int positiveDelta = 0;

  @JsonProperty("negativeDelta")
  private int negativeDelta = 0;

  @JsonProperty("branch")
  private String branch = "";

  @JsonProperty("files")
  private List<CommitDataFiles> files = null;

  public CommitData(
          int positiveDelta,
          int negativeDelta,
          List<CommitDataFiles> files
  ) {
      this.positiveDelta = positiveDelta;
      this.negativeDelta = negativeDelta;
      this.files = files;
  }

  public CommitData(
          String committer,
          String message,
          BigDecimal committedAt,
          List<CommitDataFiles> files
  ) {
      this.committer = committer;
      this.message = message;
      this.committedAt = committedAt;
      this.files = files;
  }

  public CommitData committedAt(BigDecimal committedAt) {
    this.committedAt = committedAt;
    return this;
  }

   /**
   * Get committedAt
   * @return committedAt
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getCommittedAt() {
    return committedAt;
  }

  public void setCommittedAt(BigDecimal committedAt) {
    this.committedAt = committedAt;
  }

  public CommitData message(String message) {
    this.message = message;
    return this;
  }

   /**
   * Get message
   * @return message
  **/
  @ApiModelProperty(value = "")


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public CommitData committer(String committer) {
    this.committer = committer;
    return this;
  }

   /**
   * Get committer
   * @return committer
  **/
  @ApiModelProperty(value = "")


  public String getCommitter() {
    return committer;
  }

  public void setCommitter(String committer) {
    this.committer = committer;
  }

  public CommitData positiveDelta(int positiveDelta) {
    this.positiveDelta = positiveDelta;
    return this;
  }

   /**
   * Get positiveDelta
   * @return positiveDelta
  **/
  @ApiModelProperty(value = "")

  @Valid

  public int getPositiveDelta() {
    return positiveDelta;
  }

  public void setPositiveDelta(int positiveDelta) {
    this.positiveDelta = positiveDelta;
  }

  public CommitData negativeDelta(int negativeDelta) {
    this.negativeDelta = negativeDelta;
    return this;
  }

   /**
   * Get negativeDelta
   * @return negativeDelta
  **/
  @ApiModelProperty(value = "")

  @Valid

  public int getNegativeDelta() {
    return negativeDelta;
  }

  public void setNegativeDelta(int negativeDelta) {
    this.negativeDelta = negativeDelta;
  }

  public CommitData branch(String branch) {
    this.branch = branch;
    return this;
  }

   /**
   * Get branch
   * @return branch
  **/
  @ApiModelProperty(value = "")


  public String getBranch() {
    return branch;
  }

  public void setBranch(String branch) {
    this.branch = branch;
  }

  public CommitData files(List<CommitDataFiles> files) {
    this.files = files;
    return this;
  }

  public CommitData addFilesItem(CommitDataFiles filesItem) {
    if (this.files == null) {
      this.files = new ArrayList<CommitDataFiles>();
    }
    this.files.add(filesItem);
    return this;
  }

   /**
   * Get files
   * @return files
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<CommitDataFiles> getFiles() {
    return files;
  }

  public void setFiles(List<CommitDataFiles> files) {
    this.files = files;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommitData commitData = (CommitData) o;
    return Objects.equals(this.committedAt, commitData.committedAt) &&
        Objects.equals(this.message, commitData.message) &&
        Objects.equals(this.committer, commitData.committer) &&
        Objects.equals(this.positiveDelta, commitData.positiveDelta) &&
        Objects.equals(this.negativeDelta, commitData.negativeDelta) &&
        Objects.equals(this.branch, commitData.branch) &&
        Objects.equals(this.files, commitData.files);
  }

  @Override
  public int hashCode() {
    return Objects.hash(committedAt, message, committer, positiveDelta, negativeDelta, branch, files);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommitData {\n");
    
    sb.append("    committedAt: ").append(toIndentedString(committedAt)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    committer: ").append(toIndentedString(committer)).append("\n");
    sb.append("    positiveDelta: ").append(toIndentedString(positiveDelta)).append("\n");
    sb.append("    negativeDelta: ").append(toIndentedString(negativeDelta)).append("\n");
    sb.append("    branch: ").append(toIndentedString(branch)).append("\n");
    sb.append("    files: ").append(toIndentedString(files)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

