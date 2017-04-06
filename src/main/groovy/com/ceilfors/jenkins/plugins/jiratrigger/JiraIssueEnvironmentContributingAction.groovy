package com.ceilfors.jenkins.plugins.jiratrigger

import com.atlassian.jira.rest.client.api.domain.Issue
import com.atlassian.jira.rest.client.api.domain.IssueField
import hudson.EnvVars
import hudson.model.AbstractBuild
import hudson.model.EnvironmentContributingAction

/**
 * @author ceilfors
 */
class JiraIssueEnvironmentContributingAction implements EnvironmentContributingAction {

    String issueKey
    String issueTypeName
    String issueRelatedClientName

    final String clientFieldNameKey = 'value'
    final String clientFieldIDString = '10902'

    JiraIssueEnvironmentContributingAction(Issue issue) {

        this.issueKey = issue?.key

        // Getting the Issue Type String i.e.: New Feature, Improvements etc...            
        this.issueTypeName = issue?.issueType?.name

        // Getting the Client Name i.e.: New Feature, Improvements etc...
        IssueField issueField = issue?.getField(clientFieldIDString)
        this.issueRelatedClientName = issueField?.name
    }

    @Override
    void buildEnvVars(AbstractBuild<?, ?> build, EnvVars env) {
        env.put('JIRA_ISSUE_KEY', issueKey)

        if (this.issueTypeName) {
            env.put('JIRA_ISSUE_TYPE', issueTypeName)
        }

        if (this.issueRelatedClientName) {
            env.put('JIRA_CLIENT_NAME', issueRelatedClientName)
        }
    }

    @Override
    String getIconFileName() {
        null
    }

    @Override
    String getDisplayName() {
        null
    }

    @Override
    String getUrlName() {
        null
    }
}
