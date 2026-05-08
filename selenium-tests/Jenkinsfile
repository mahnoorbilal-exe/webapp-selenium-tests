pipeline {

    agent any

    stages {

        stage('Clone Repository') {
            steps {
                git 'https://github.com/mahnoorbilal-exe/webapp-selenium-tests.git'
            }
        }

        stage('Run Selenium Tests') {

            steps {

                script {

                    docker.image('markhobson/maven-chrome').inside {

                        sh 'mvn test'

                    }
                }
            }
        }
    }

    post {

        always {

            junit '**/target/surefire-reports/*.xml'

            emailext(
                subject: "Selenium Test Results - ${currentBuild.currentResult}",
                body: """
                Jenkins Pipeline Execution Completed

                Job Name: ${JOB_NAME}
                Build Number: ${BUILD_NUMBER}
                Build Status: ${currentBuild.currentResult}

                Check Jenkins dashboard for complete logs and reports.
                """,
                to: "mahnoor.bilal2240@gmail.com"
            )
        }
    }
}