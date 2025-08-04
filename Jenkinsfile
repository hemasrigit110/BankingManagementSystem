pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/hemasrigit110/BankingManagementSystem.git', branch: 'main'
            }
        }

        stage('Build at Root') {
            steps {
                sh 'mvn clean package'
            }
        }
        
        stage('Run Discovery Server') {
            steps {
                dir('/var/lib/jenkins/workspace/test@2/discovery-server/target') {
                    sh 'nohup java -jar discovery-server-1.0.0-SNAPSHOT.jar > discovery.log 2>&1 &'
                }
            }
        }

        stage('Run API Gateway') {
            steps {
                dir('Api-gateway/target') {
                    sh 'nohup java -jar api-gateway-1.0.0-SNAPSHOT.jar > apigateway.log 2>&1 &'
                }
            }
        }

        stage('Run Auth Service') {
            steps {
                dir('banking-auth-service/target') {
                    sh 'nohup java -jar banking-auth-service-1.0.0-SNAPSHOT.jar > authservice.log 2>&1 &'
                }
            }
        }

        stage('Run Account Service') {
            steps {
                dir('banking-account-service/target') {
                    sh 'nohup java -jar banking-account-service-1.0.0-SNAPSHOT.jar > accountservice.log 2>&1 &'
                }
            }
        }

        stage('Run Transaction Service') {
            steps {
                dir('transaction-service/target') {
                    sh 'nohup java -jar transaction-service-1.0.0-SNAPSHOT.jar > transactionservice.log 2>&1 &'
                }
            }
        }
    }
}
