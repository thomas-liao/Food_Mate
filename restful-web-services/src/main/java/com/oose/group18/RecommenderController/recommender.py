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
    def __init__ (self, type, file_path, line_format='user item rating timestamp', sep=' ', forget_rate=0.1):
        self.algo = SVD()
        self.reader = Reader(line_format=line_format, sep=sep)
        self.file_path = file_path
        self.data = None
        self.trainset = None
        self.testset = None
        self.userSim = None
        self.nUser = None
        self.nItem = None
        self.forget_rate = forget_rate

        self.merge_data()
        self.load_data()

    def getNumUser (self):
        return self.nUser

    def getNumItem (self):
        return self.nItem

    def merge_data (self):
        with open(self.file_path, 'r') as f:
            rating = f.readlines()
        
        rating_mat = {}
        for line in rating:
            content = line.strip().split(' ')
            uid = content[0]
            iid = content[1]
            score = float(content[2])
            if not iid in rating_mat.setdefault(uid, {}):
                rating_mat[uid][iid] = score
            else:
                rating_mat[uid][iid] = self.forget_rate * rating_mat[uid][iid] + (1 - self.forget_rate) * score
            
        with open(self.file_path, 'w') as f:
            for uid in rating_mat:
                for iid in rating_mat[uid]:
                    f.write("{} {} {:.15f} timestamp\n".format(uid, iid, rating_mat[uid][iid]))

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

        self.nUser = len(self.trainset.all_users())
        self.nItem = len(self.trainset.all_items())
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
        ranked_list_with_score = [(iid,iid_to_score[iid]) for iid in ranked_list]
        #pdb.set_trace()
        return ranked_list_with_score

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

    def save_usersim (self, path):
        with open(path, 'w') as f:
            for i in range(self.userSim.shape[0]):
                for j in range(self.userSim.shape[1]):
                    f.write('{:.3f} '.format(self.userSim[i, j]))
                f.write('\n')

def load_saved_model(path):
    with open(path, 'rb') as f:
        model = pickle.load(f)
    return model

def main():
    args = parse_args()
    if args.train:
        recomm = Recommender('SVD', args.rating_data)
        recomm.train()
        #recomm.save_usersim('weight.pkl')
        #recomm.save_usersim('user_sim.txt')
        recomm.save_weight('./src/main/java/com/oose/group18/RecommenderController/weight.pkl')
        recomm.save_usersim('./src/main/java/com/oose/group18/RecommenderController/user_sim.txt')
    else:
        #recomm = load_saved_model('weight.pkl')
        recomm = load_saved_model('./src/main/java/com/oose/group18/RecommenderController/weight.pkl')
        ranked_list_with_score = recomm.recommend_for_user(args.uid)
        for i in range(min(args.topk, recomm.getNumItem())):
            print( '{} {:.3f}'.format( ranked_list_with_score[i][0], ranked_list_with_score[i][1] ))

    if args.debug:
        recomm.split_data(test_size=args.test_size)
        recomm.train(use_full_data=False)
        recomm.test()


if __name__ == '__main__':
    main()
