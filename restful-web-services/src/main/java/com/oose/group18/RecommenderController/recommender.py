import os
import argparse
import numpy as np
import pickle
from surprise import SVD 
from surprise import Dataset
from surprise.model_selection import cross_validate
from surprise import accuracy
from surprise.model_selection import train_test_split
from surprise import Reader
import pdb

def parse_args():
  parser = argparse.ArgumentParser()

  parser.add_argument(
      '--train',
      action='store_true',
      help='rating-data file path'
  )
  parser.add_argument(
      '--rating-data',
      type=str,
      help='rating-data file path'
  )
  parser.add_argument(
      '--uid',
      type=str,
      help='user id for recommendation'
  )
  parser.add_argument(
      '--debug',
      dest='debug',
      action='store_true',
      help='whether perform test'
  )
  parser.add_argument(
      '--test-size',
      type=float,
      help='percentage for test'
  )
  parser.add_argument(
      '--topk',
      type=int,
      default=10,
      help='return top-k items'
  )

  args = parser.parse_args()
  return args

class Recommender:
    '''

    '''
    def __init__ (self, type, file_path, line_format='user item rating timestamp', sep=' '):
        self.algo = SVD()
        self.reader = Reader(line_format=line_format, sep=sep)
        self.file_path = file_path
        self.data = None
        self.trainset = None
        self.testset = None
        self.userSim = None
        
        self.load_data()

    def load_data (self):
        self.data = Dataset.load_from_file(self.file_path, self.reader)
    
    def split_data (self, test_size=0.05):
        self.trainset, self.testset = train_test_split(self.data, test_size=test_size)

    def train (self, use_full_data=True):
        if use_full_data:
            trainset = self.data.build_full_trainset()
            self.trainset = trainset
        else:
            trainset = self.trainset

        self.algo.fit(trainset)

        self.userSim = np.matmul(self.algo.pu, self.algo.pu.T)
        self.userSim = self.userSim - np.diag(self.userSim.diagonal())

    def test (self):
        predictions = self.algo.test(self.testset)
        accuracy.rmse(predictions)
        #acc = accuracy.rmse(predictions)
        #print("RMSE: {:.3f}".format(acc))

    def predict (self, uid, iid):
        p = self.algo.predict(uid, iid)
        return p.est

    def recommend_for_user (self, uid):
        iid_to_score = {}
        for item in self.trainset.all_items():
            iid = self.trainset.to_raw_iid(item)
            score = self.algo.predict(uid, iid).est
            iid_to_score[iid] = score

        ranked_list = sorted(list(iid_to_score.keys()), key=lambda iid: iid_to_score[iid], reverse=True)
        #pdb.set_trace()
        return ranked_list

    def user_similarity (self, uid1, uid2):
        '''
        return similarity of two users
        '''
        if self.userSim is not None:
            return self.userSim[uid1, uid2]
        else:
            raise NotImplementedError
            return 0

    def save_weight (self, path):
        pickle.dump( self, open( path, "wb" ) )

def load_weight(path):
    with open(path, 'rb') as f:
        model = pickle.load(f)
    return model

def main():
    args = parse_args()
    if args.train:
        recomm = Recommender('SVD', args.rating_data)
        recomm.train()
        recomm.save_weight('./src/main/java/com/oose/group18/RecommenderController/weight.pkl')
    else:
        recomm = load_weight('./src/main/java/com/oose/group18/RecommenderController/weight.pkl')
        ranked_list = recomm.recommend_for_user(args.uid)
        for i in range(args.topk):
            print(ranked_list[i])

    if args.debug:
        recomm.split_data(test_size=args.test_size)
        recomm.train(use_full_data=False)
        recomm.test()


if __name__ == '__main__':
    main()