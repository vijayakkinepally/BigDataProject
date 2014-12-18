from tweepy import Stream
from tweepy import OAuthHandler
from tweepy.streaming import StreamListener
import datetime
import time
import json
import os
import io
import pymongo
from pymongo import MongoClient

ckey = 'RtYeBD5EOol2uDq59qnS58hX1'
csecret = 'xS1HC5Ag5tin0YCEasKAdJJvGe0sZEHbrXH86pLjw3UkMluZXQ'
atoken = '169473602-Ly3wrCfnx22BtEFSiPFmIY68au8pIJ036FE9BRiO'
asecret = 't7Fomd6f2V0wwntHGMjoFkG0EHG8CJrEihIno5z7mq35d'
    

class listener(StreamListener):      

    def __init__(self, api = None, fprefix = 'Finaltweets'):
        self.api = api
        self.counter = 0
        self.fprefix = fprefix
        self.fname  = fprefix + '.' + time.strftime('%Y%m%d-%H%M%S') + '.json'

    def on_status(self, status):
        
        tags = []

        for hashtag in status.entities['hashtags']:
            tags.append(hashtag['text'])

	tweet = json(status);
	collection.insert({'tweet':tweet})

        self.counter += 1
        if self.counter < 20000000:
            with open(self.fname, 'a') as f:
                json.dump(str(tweet), f)
                f.write(os.linesep)
        else:
            self.fname = self.fprefix + '.' + time.strftime('%Y%m%d-%H%M%S') + '.json'
            with open(self.fname, 'a') as f:
                json.dump(str(tweet), f)
                f.write(os.linesep)
            self.counter = 0

        return True

    def on_error(self, status_code):
        print >> sys.stderr, 'Encountered an error and the status code is :', status_code
        return True
        print "Stream will be restarted"

    def on_timeout(self):
        print >> sys.stderr, 'Timeout...'
        return True
        print "Stream will be restarted"

if __name__ == '__main__':
	client = MongoClient('localhost', 27017)
db = client.ebola_tweets
collection = db.ebola_tweet_data
l = listener()
auth = OAuthHandler(ckey, csecret)
auth.set_access_token(atoken, asecret)
twitterStream = Stream(auth, l)
twitterStream.filter(track = ["ebola"])
    
