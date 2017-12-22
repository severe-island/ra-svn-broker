package org.repoaggr.svnbrk.api;

import io.swagger.annotations.*;
import org.repoaggr.svnbrk.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;

import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-12-09T09:35:53.477Z")

@Api(value = "repositories", description = "the repositories API")
public interface RepositoriesApi {

    @ApiOperation(value = "Returns simple branch.", notes = "", response = Branch.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Branch.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class) })
    
    @RequestMapping(
            value = "/repositories/{id}/branches/{branch_id}",
            method = RequestMethod.GET)
    ResponseEntity<Branch> repositoriesIdBranchesBranchIdGet(
            @ApiParam(value = "",required=true ) @PathVariable("id") String id,
            @ApiParam(value = "",required=true ) @PathVariable("branch_id") String branchId);


    @ApiOperation(value = "Returns a list of branches.", notes = "", response = List.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = List.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class) })
    
    @RequestMapping(
            value = "/repositories/{id}/branches",
            method = RequestMethod.GET)
    ResponseEntity<BrokerList> repositoriesIdBranchesGet(
            @ApiParam(value = "",required=true ) @PathVariable("id") String id);


    @ApiOperation(value = "Returns simple commit.", notes = "", response = Commit.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Commit.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class) })
    
    @RequestMapping(
            value = "/repositories/{id}/commits/{commit_id}",
            method = RequestMethod.GET)
    ResponseEntity<Commit> repositoriesIdCommitCommitIdGet(
            @ApiParam(value = "",required=true ) @PathVariable("id") String id,
            @ApiParam(value = "",required=true ) @PathVariable("commit_id") String commitId);


    @ApiOperation(value = "Returns a list of commits.", notes = "", response = List.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = List.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class) })
    
    @RequestMapping(
            value = "/repositories/{id}/commits",
            method = RequestMethod.GET)
    ResponseEntity<BrokerList> repositoriesIdCommitsGet(
            @ApiParam(value = "",required=true ) @PathVariable("id") String id);


    @ApiOperation(value = "Returns a repository overview.", notes = "", response = Overview.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Overview.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class) })
    
    @RequestMapping(
            value = "/repositories/{id}",
            method = RequestMethod.GET)
    ResponseEntity<Overview> repositoriesIdGet(@ApiParam(value = "",required=true ) @PathVariable("id") String id);


    @ApiOperation(value = "Register of repository.", notes = "Register of repository.",
            response = RegistrationStatus.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created", response = RegistrationStatus.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class) })
    
    @RequestMapping(
            value = "/repositories",
            method = RequestMethod.POST)
    ResponseEntity<RegistrationStatus> repositoriesPost(
            @ApiParam(value = "", required=true) @RequestPart(value="url", required=true) String url,
            @ApiParam(value = "", required=true) @RequestPart(value="id", required=true) String id,
            @ApiParam(value = "") @RequestPart(value="login", required=false) String login,
            @ApiParam(value = "") @RequestPart(value="password", required=false) String password);


    @ApiOperation(value = "Returns a list of registered repositories", notes = "",
            response = BrokerList.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = BrokerList.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)})

    @RequestMapping(
            value = "/repositories",
            method = RequestMethod.GET)
    ResponseEntity<BrokerList> repositoriesGet();

}
