package org.repoaggr.svnbrk.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CommitDataFiles
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-12-07T13:58:22.696Z")

public class CommitDataFiles   {
  @JsonProperty("size")
  private BigDecimal size = null;

  @JsonProperty("flag")
  private String flag = null;

  @JsonProperty("positiveDelta")
  private BigDecimal positiveDelta = null;

  @JsonProperty("negativeDelta")
  private BigDecimal negativeDelta = null;

  @JsonProperty("path")
  private String path = null;

  public CommitDataFiles size(BigDecimal size) {
    this.size = size;
    return this;
  }

   /**
   * Get size
   * @return size
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getSize() {
    return size;
  }

  public void setSize(BigDecimal size) {
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

  public CommitDataFiles positiveDelta(BigDecimal positiveDelta) {
    this.positiveDelta = positiveDelta;
    return this;
  }

   /**
   * Get positiveDelta
   * @return positiveDelta
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getPositiveDelta() {
    return positiveDelta;
  }

  public void setPositiveDelta(BigDecimal positiveDelta) {
    this.positiveDelta = positiveDelta;
  }

  public CommitDataFiles negativeDelta(BigDecimal negativeDelta) {
    this.negativeDelta = negativeDelta;
    return this;
  }

   /**
   * Get negativeDelta
   * @return negativeDelta
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getNegativeDelta() {
    return negativeDelta;
  }

  public void setNegativeDelta(BigDecimal negativeDelta) {
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

