package custom.repo

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.ArtifactRepository

class CustomRepoPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        addCustomRepoDslTo(project)
    }

    private void addCustomRepoDslTo(Project project) {
        def repositories = project.getRepositories()
        repositories.ext.myRepo = {
            repositories.add(createMyRepo(project))
        }
    }

    private ArtifactRepository createMyRepo(Project project) {
        def myRepo = project.repositories.ivy {
            name 'myRepo'
            url "file:/${project.rootProject.projectDir}/../repo"
            layout 'pattern', {
                artifact "[organisation]/[module]/[revision]/lib/[artifact].[ext]"
                ivy "[organisation]/[module]/[revision]/etc/ivy.xml"
            }
        }
        myRepo.resolve

        return myRepo
    }
}
