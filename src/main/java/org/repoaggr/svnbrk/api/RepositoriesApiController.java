package org.repoaggr.svnbrk.api;

import org.repoaggr.svnbrk.model.Branch;
import org.repoaggr.svnbrk.model.Commit;
import java.util.List;
import org.repoaggr.svnbrk.model.Overview;
import org.repoaggr.svnbrk.model.RegistrationStatus;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.validation.constraints.*;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-12-07T13:58:22.696Z")

@Controller
public class RepositoriesApiController implements RepositoriesApi {



    public ResponseEntity<Branch> repositoriesIdBranchesBranchIdGet(@ApiParam(value = "",required=true ) @PathVariable("id") String id,
        @ApiParam(value = "",required=true ) @PathVariable("branch_id") String branchId) {
        // do some magic!
        return new ResponseEntity<Branch>(HttpStatus.OK);
    }

    public ResponseEntity<List> repositoriesIdBranchesGet(@ApiParam(value = "",required=true ) @PathVariable("id") String id) {
        // do some magic!
        return new ResponseEntity<List>(HttpStatus.OK);
    }

    public ResponseEntity<Commit> repositoriesIdCommitCommitIdGet(@ApiParam(value = "",required=true ) @PathVariable("id") String id,
        @ApiParam(value = "",required=true ) @PathVariable("commit_id") String commitId) {
        // do some magic!
        return new ResponseEntity<Commit>(HttpStatus.OK);
    }

    public ResponseEntity<List> repositoriesIdCommitsGet(@ApiParam(value = "",required=true ) @PathVariable("id") String id) {
        // do some magic!
        return new ResponseEntity<List>(HttpStatus.OK);
    }

    public ResponseEntity<Overview> repositoriesIdGet(@ApiParam(value = "",required=true ) @PathVariable("id") String id) {
        // do some magic!
        return new ResponseEntity<Overview>(HttpStatus.OK);
    }

    public ResponseEntity<RegistrationStatus> repositoriesPost(@ApiParam(value = "", required=true) @RequestPart(value="url", required=true)  String url,
        @ApiParam(value = "", required=true) @RequestPart(value="login", required=true)  String login,
        @ApiParam(value = "", required=true) @RequestPart(value="password", required=true)  String password,
        @ApiParam(value = "", required=true) @RequestPart(value="id", required=true)  String id) {
        // do some magic!
        return new ResponseEntity<RegistrationStatus>(HttpStatus.OK);
    }

}
