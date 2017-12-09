package org.repoaggr.svnbrk.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CommitDataFiles
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-12-09T09:35:53.477Z")

public class CommitDataFiles   {
  @JsonProperty("size")
  private int size = 0;

  @JsonProperty("flag")
  private String flag = null;

  @JsonProperty("positiveDelta")
  private int positiveDelta = 0;

  @JsonProperty("negativeDelta")
  private int negativeDelta = 0;

  @JsonProperty("path")
  private String path = null;

  public CommitDataFiles(
          int size,
          String flag,
          int positiveDelta,
          int negativeDelta,
          String path
  ) {
      this.size = size;
      this.flag = flag;
      this.positiveDelta = positiveDelta;
      this.negativeDelta = negativeDelta;
      this.path = path;
  }

  public CommitDataFiles size(int size) {
    this.size = size;
    return this;
  }

   /**
   * Get size
   * @return size
  **/
  @ApiModelProperty(value = "")

  @Valid

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public CommitDataFiles flag(String flag) {
    this.flag = flag;
    return this;
  }

   /**
   * Get flag
   * @return flag
  **/
  @ApiModelProperty(value = "")

 @Pattern(regexp="A|D|M|R")
  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  public CommitDataFiles positiveDelta(int positiveDelta) {
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

  public CommitDataFiles negativeDelta(int negativeDelta) {
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

  public CommitDataFiles path(String path) {
    this.path = path;
    return this;
  }

   /**
   * Get path
   * @return path
  **/
  @ApiModelProperty(value = "")


  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommitDataFiles commitDataFiles = (CommitDataFiles) o;
    return Objects.equals(this.size, commitDataFiles.size) &&
        Objects.equals(this.flag, commitDataFiles.flag) &&
        Objects.equals(this.positiveDelta, commitDataFiles.positiveDelta) &&
        Objects.equals(this.negativeDelta, commitDataFiles.negativeDelta) &&
        Objects.equals(this.path, commitDataFiles.path);
  }

  @Override
  public int hashCode() {
    return Objects.hash(size, flag, positiveDelta, negativeDelta, path);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommitDataFiles {\n");
    
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    flag: ").append(toIndentedString(flag)).append("\n");
    sb.append("    positiveDelta: ").append(toIndentedString(positiveDelta)).append("\n");
    sb.append("    negativeDelta: ").append(toIndentedString(negativeDelta)).append("\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
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

