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
 * BranchData
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-12-09T09:35:53.477Z")

public class BranchData   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("status")
  private String status = null;

  @JsonProperty("created_at")
  private BigDecimal createdAt = null;

  @JsonProperty("initial_commit")
  private String initialCommit = null;

  @JsonProperty("author")
  private String author = null;

  public BranchData(
          String name,
          String status,
          BigDecimal createdAt,
          String initialCommit,
          String author
  ) {
      this.name = name;
      this.status = status;
      this.createdAt = createdAt;
      this.initialCommit = initialCommit;
      this.author = author;
  }

  public BranchData name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BranchData status(String status) {
    this.status = status;
    return this;
  }

   /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(value = "")

 @Pattern(regexp="unlocked|locked")
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public BranchData createdAt(BigDecimal createdAt) {
    this.createdAt = createdAt;
    return this;
  }

   /**
   * Get createdAt
   * @return createdAt
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(BigDecimal createdAt) {
    this.createdAt = createdAt;
  }

  public BranchData initialCommit(String initialCommit) {
    this.initialCommit = initialCommit;
    return this;
  }

   /**
   * Get initialCommit
   * @return initialCommit
  **/
  @ApiModelProperty(value = "")


  public String getInitialCommit() {
    return initialCommit;
  }

  public void setInitialCommit(String initialCommit) {
    this.initialCommit = initialCommit;
  }

  public BranchData author(String author) {
    this.author = author;
    return this;
  }

   /**
   * Get author
   * @return author
  **/
  @ApiModelProperty(value = "")


  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BranchData branchData = (BranchData) o;
    return Objects.equals(this.name, branchData.name) &&
        Objects.equals(this.status, branchData.status) &&
        Objects.equals(this.createdAt, branchData.createdAt) &&
        Objects.equals(this.initialCommit, branchData.initialCommit) &&
        Objects.equals(this.author, branchData.author);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, status, createdAt, initialCommit, author);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BranchData {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    initialCommit: ").append(toIndentedString(initialCommit)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
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

