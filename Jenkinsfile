pipeline {
    agent {
        docker {
            image 'maven:3.9.6-eclipse-temurin-21'
            args '-v /var/jenkins_home/.m2:/root/.m2'
        }
    }

    stages {
        stage('Checkout') {
            steps {
                git credentialsId: 'github-https-pat', branch: 'master', url: 'https://github.com/necromancer404/devops.git'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn -Dmaven.repo.local=$WORKSPACE/.m2repo clean package'
            }
        }

        stage('Archive Artifacts and Reports') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                // junit 'target/surefire-reports/*.xml'
            }
        }
    }
}
