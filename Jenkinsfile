#!groovy

@Library('jenkins-library@master') _

pipeline {
  agent {
    kubernetes {
      inheritFrom 'maven'
    }
  }


  stages {
    stage('build') {
      steps {
        sh 'mvn clean install'
      }
    }
  }
}
