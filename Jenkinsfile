pipeline {
    agent {
        docker {
            image 'maven:3.9.6-eclipse-temurin-21'
            args '-v $HOME/.m2:/root/.m2'
        }
    }

    stages {
        stage('Checkout') {
            steps {
                git credentialsId: 'github-https-pat', branch: 'master', url: 'https://github.com/necromancer404/devops.git'
            }
        }

        stage('Build') {
   		 	steps {
      		  	sh 'mvn -Dmaven.repo.local=target/.m2repo clean package'
    		}
		}


        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                junit 'target/surefire-reports/*.xml'
            }
        }
    }
}
