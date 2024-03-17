#!groovy

@Library('shared-library') _

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
