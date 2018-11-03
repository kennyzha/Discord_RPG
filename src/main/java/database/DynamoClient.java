package database;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import config.ApplicationConstants;

public class DynamoClient {
    private AWSCredentialsProvider awsCredentialsProvider;
    private AmazonDynamoDB amazonDynamoDB;
    private DynamoDB dynamoDb;

    private String awsAcessKeyID = ApplicationConstants.AWS_ACCESS_KEY;
    private String awsSecretKey = ApplicationConstants.AWS_SECRET_ACCESS_KEY;

//    private String playerTableName = ApplicationConstants.PLAYER_TABLE_NAME;
    private String playerTableName = ApplicationConstants.PLAYER_DEV_TABLE_NAME;
    private String staminaTableName = ApplicationConstants.STAMINA_TABLE_NAME;

    private String playerSpeedIndex = ApplicationConstants.PLAYER_SPEED_INDEX;
    private String playerPowerIndex = ApplicationConstants.PLAYER_POWER_INDEX;
    private String playerStrengthIndex = ApplicationConstants.PLAYER_STRENGTH_INDEX;

    public DynamoClient(){
        awsCredentialsProvider = new AWSStaticCredentialsProvider(new AWSCredentials() {
            public String getAWSAccessKeyId() {
                return awsAcessKeyID;
            }

            public String getAWSSecretKey() {
                return awsSecretKey;
            }
        });

        amazonDynamoDB = AmazonDynamoDBClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(awsCredentialsProvider)
                .build();

        dynamoDb = new DynamoDB(amazonDynamoDB);
    }

    public AWSCredentialsProvider getAwsCredentialsProvider() {
        return awsCredentialsProvider;
    }

    public AmazonDynamoDB getAmazonDynamoDB() {
        return amazonDynamoDB;
    }

    public DynamoDB getDynamoDb() {
        return dynamoDb;
    }

    public String getPlayerTableName(){
        return playerTableName;
    }

    public String getStaminaTableName(){
        return staminaTableName;
    }

    public String getPlayerSpeedIndex() {
        return playerSpeedIndex;
    }

    public String getPlayerPowerIndex() {
        return playerPowerIndex;
    }

    public String getPlayerStrengthIndex() {
        return playerStrengthIndex;
    }


}
