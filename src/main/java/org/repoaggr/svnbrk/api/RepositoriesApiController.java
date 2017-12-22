package org.repoaggr.svnbrk.api;

import org.repoaggr.svnbrk.controller.MainController;
import org.repoaggr.svnbrk.model.*;

import java.io.IOException;
import java.util.List;

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

import org.repoaggr.svnbrk.controller.RemoteSvnController;
import org.tmatesoft.svn.core.SVNException;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-12-09T09:35:53.477Z")

@Controller
public class RepositoriesApiController implements RepositoriesApi {

    public ResponseEntity<Branch> repositoriesIdBranchesBranchIdGet(
            @ApiParam(value = "",required=true ) @PathVariable("id") String id,
            @ApiParam(value = "",required=true ) @PathVariable("branch_id") String branchId)
    {
        return MainController.getBranch(id, branchId);
    }

    public ResponseEntity<BrokerList> repositoriesIdBranchesGet(
            @ApiParam(value = "",required=true ) @PathVariable("id") String id)
    {
        return MainController.getBranchesList(id);
    }

    public ResponseEntity<Commit> repositoriesIdCommitCommitIdGet(
            @ApiParam(value = "",required=true ) @PathVariable("id") String id,
            @ApiParam(value = "",required=true ) @PathVariable("commit_id") String commitId)
    {
        return MainController.getCommit(id, commitId);
    }

    public ResponseEntity<BrokerList> repositoriesIdCommitsGet(
            @ApiParam(value = "",required=true ) @PathVariable("id") String id)
    {
        return MainController.getCommitsList(id);
    }

    public ResponseEntity<Overview> repositoriesIdGet(
            @ApiParam(value = "",required=true ) @PathVariable("id") String id)
    {
        return MainController.getOverview(id);
    }

    public ResponseEntity<RegistrationStatus> repositoriesPost(
            @ApiParam(value = "", required=true) @RequestPart(value="url", required=true)  String url,
            @ApiParam(value = "", required=true) @RequestPart(value="id", required=true)  String id,
            @ApiParam(value = "") @RequestPart(value="login", required=false)  String login,
            @ApiParam(value = "") @RequestPart(value="password", required=false)  String password)
    {
        return MainController.postRegistrationStatus(url, login, password, id);
    }

    public ResponseEntity<BrokerList> repositoriesGet() {
        return MainController.getRepositories();
    }
}
