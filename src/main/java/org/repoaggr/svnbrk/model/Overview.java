package org.repoaggr.svnbrk.model;

import java.io.Serializable;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.repoaggr.svnbrk.model.OverviewData;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * A representation of some SVN-repository
 */
@ApiModel(description = "A representation of some SVN-repository")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-12-09T09:35:53.477Z")

public class Overview implements Serializable {
  @JsonProperty("status")
  private String status = null;

  @JsonProperty("reason")
  private String reason = null;

  @JsonProperty("data")
  private OverviewData data = null;

  public Overview(String status, String reason) {
      this.status = status;
      this.reason = reason;
  }

  public Overview status(String status) {
    this.status = status;
    return this;
  }

   /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

 @Pattern(regexp="success|warning|failure")
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Overview reason(String reason) {
    this.reason = reason;
    return this;
  }

   /**
   * Get reason
   * @return reason
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public Overview data(OverviewData data) {
    this.data = data;
    return this;
  }

   /**
   * Get data
   * @return data
  **/
  @ApiModelProperty(value = "")

  @Valid

  public OverviewData getData() {
    return data;
  }

  public void setData(OverviewData data) {
    this.data = data;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Overview overview = (Overview) o;
    return Objects.equals(this.status, overview.status) &&
        Objects.equals(this.reason, overview.reason) &&
        Objects.equals(this.data, overview.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, reason, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Overview {\n");
    
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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

