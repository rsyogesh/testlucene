  node{
   stage('SCM Checkout'){
     git 'https://github.com/rsyogesh/testlucene'
   }
   stage('Compile-Package'){
    
      def mvnHome =  tool name: 'Maven', type: 'maven'   
      sh "${mvnHome}/bin/mvn package"
   }
   stage('Email Notification'){
      mail bcc: '', body: '''Hi Welcome to jenkins email alerts
      Thanks
      Hari''', cc: '', from: '', replyTo: '', subject: 'Jenkins Job', to: 'rsyogesh@gmail.com'
   }
   
}

