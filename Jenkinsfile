def githubUrl = "https://github.com/Corgis-Team/CorgiRoulette"
def tomcatServerUrl = "http://34.163.228.60:8080/"
def tomcatCredentialsId = "7f9fd2fc-ceda-49b1-babf-0dc994e39f00"
def tomcatContextPath = "/CorgiRoulette"
pipeline {
  agent any
  tools {
    maven 'Maven3.8.6'
  }
  stages {
    stage ('Git') {
      steps{
        echo "Git step in process"
        git branch: '21_data', url: "${githubUrl}"
        echo "Git step is finished"
      }
    }
    stage ('Build') {
      steps {
        echo "Build in process"
        sh 'mvn clean install'
        echo "Build is finished"
      }
    }
    stage ('Deploy') {
      steps {
        echo "Deploying is in process"
        script {
          deploy adapters: [tomcat9(credentialsId: "${tomcatCredentialsId}", path: '', url: "${tomcatServerUrl}")], contextPath: "${tomcatContextPath}", onFailure: false, war: 'target/*.war'
        }
        echo "Deploy is finished"
      }
    }
  }
}