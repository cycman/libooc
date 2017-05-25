package com.cyc.recoment;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.recommender.ClusterSimilarity;
import org.apache.mahout.cf.taste.impl.recommender.FarthestNeighborClusterSimilarity;
import org.apache.mahout.cf.taste.impl.recommender.knn.NonNegativeQuadraticOptimizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * 
 * @author bsspirit@gmail.com {@link http
 *         ://blog.fens.me/mahout-recommendation-api/}
 *
 */
public class RecommenderCore {

	final static int NEIGHBORHOOD_NUM = 2;
	final static int RECOMMENDER_NUM = 10;

	public static void main(String[] args) throws TasteException, IOException {
		RandomUtils.useTestSeed();
		String file = "datafile/item.csv";
		// DataModel dataModel = RecommendFactory.buildDataModel(file);
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setURL("jdbc:MySQL://localhost:3306/libooc");
		dataSource.setUser("root");
		dataSource.setPassword("597318");
		try {
			dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataModel sqlDataModel = new MySQLJDBCDataModel(dataSource,
				"user_item", "uid", "item_id", "preference", "time");
		itemCF(sqlDataModel);
	}

	public static void userCF(DataModel dataModel) throws TasteException {
		UserSimilarity userSimilarity = RecommendFactory.userSimilarity(
				RecommendFactory.SIMILARITY.EUCLIDEAN, dataModel);
		UserNeighborhood userNeighborhood = RecommendFactory.userNeighborhood(
				RecommendFactory.NEIGHBORHOOD.NEAREST, userSimilarity,
				dataModel, NEIGHBORHOOD_NUM);
		RecommenderBuilder recommenderBuilder = RecommendFactory
				.userRecommender(userSimilarity, userNeighborhood, true);

		RecommendFactory.evaluate(
				RecommendFactory.EVALUATOR.AVERAGE_ABSOLUTE_DIFFERENCE,
				recommenderBuilder, null, dataModel, 0.7);
		RecommendFactory.statsEvaluator(recommenderBuilder, null, dataModel, 2);

		LongPrimitiveIterator iter = dataModel.getUserIDs();
		while (iter.hasNext()) {
			long uid = iter.nextLong();
			List<RecommendedItem> list = recommenderBuilder.buildRecommender(
					dataModel).recommend(uid, RECOMMENDER_NUM);
			RecommendFactory.showItems(uid, list, true);
		}
	}

	public static void itemCF(DataModel dataModel) throws TasteException {
		ItemSimilarity itemSimilarity = RecommendFactory.itemSimilarity(
				RecommendFactory.SIMILARITY.EUCLIDEAN, dataModel);
		RecommenderBuilder recommenderBuilder = RecommendFactory
				.itemRecommender(itemSimilarity, true);

		RecommendFactory.evaluate(
				RecommendFactory.EVALUATOR.AVERAGE_ABSOLUTE_DIFFERENCE,
				recommenderBuilder, null, dataModel, 0.7);
		RecommendFactory.statsEvaluator(recommenderBuilder, null, dataModel, 2);

		LongPrimitiveIterator iter = dataModel.getUserIDs();
		while (iter.hasNext()) {
			long uid = iter.nextLong();
			List<RecommendedItem> list = recommenderBuilder.buildRecommender(
					dataModel).recommend(uid, RECOMMENDER_NUM);
			RecommendFactory.showItems(uid, list, true);
		}
	}

	public static void slopeOne(DataModel dataModel) throws TasteException {
		RecommenderBuilder recommenderBuilder = RecommendFactory
				.slopeOneRecommender();

		RecommendFactory.evaluate(
				RecommendFactory.EVALUATOR.AVERAGE_ABSOLUTE_DIFFERENCE,
				recommenderBuilder, null, dataModel, 0.7);
		RecommendFactory.statsEvaluator(recommenderBuilder, null, dataModel, 2);

		LongPrimitiveIterator iter = dataModel.getUserIDs();
		while (iter.hasNext()) {
			long uid = iter.nextLong();
			List<RecommendedItem> list = recommenderBuilder.buildRecommender(
					dataModel).recommend(uid, RECOMMENDER_NUM);
			RecommendFactory.showItems(uid, list, true);
		}
	}

	public static void itemKNN(DataModel dataModel) throws TasteException {
		ItemSimilarity itemSimilarity = RecommendFactory.itemSimilarity(
				RecommendFactory.SIMILARITY.EUCLIDEAN, dataModel);
		RecommenderBuilder recommenderBuilder = RecommendFactory
				.itemKNNRecommender(itemSimilarity,
						new NonNegativeQuadraticOptimizer(), 10);

		RecommendFactory.evaluate(
				RecommendFactory.EVALUATOR.AVERAGE_ABSOLUTE_DIFFERENCE,
				recommenderBuilder, null, dataModel, 0.7);
		RecommendFactory.statsEvaluator(recommenderBuilder, null, dataModel, 2);

		LongPrimitiveIterator iter = dataModel.getUserIDs();
		while (iter.hasNext()) {
			long uid = iter.nextLong();
			List<RecommendedItem> list = recommenderBuilder.buildRecommender(
					dataModel).recommend(uid, RECOMMENDER_NUM);
			RecommendFactory.showItems(uid, list, true);
		}
	}

	public static List<RecommendedItem> svd(long uid, DataModel dataModel)
			throws TasteException {
		RecommenderBuilder recommenderBuilder = RecommendFactory
				.svdRecommender(new ALSWRFactorizer(dataModel, 10, 0.05, 10));

		RecommendFactory.evaluate(
				RecommendFactory.EVALUATOR.AVERAGE_ABSOLUTE_DIFFERENCE,
				recommenderBuilder, null, dataModel, 0.7);
		RecommendFactory.statsEvaluator(recommenderBuilder, null, dataModel, 2);

		LongPrimitiveIterator iter = dataModel.getUserIDs();
		List<RecommendedItem> list = new ArrayList<RecommendedItem>();
		while (iter.hasNext()) {
			if (uid == iter.nextLong()) {
				list = recommenderBuilder.buildRecommender(dataModel)
						.recommend(uid, RECOMMENDER_NUM);
				RecommendFactory.showItems(uid, list, true);
			}
		}
		return list;
	}

	public static void treeCluster(DataModel dataModel) throws TasteException {
		UserSimilarity userSimilarity = RecommendFactory.userSimilarity(
				RecommendFactory.SIMILARITY.LOGLIKELIHOOD, dataModel);
		ClusterSimilarity clusterSimilarity = RecommendFactory
				.clusterSimilarity(
						RecommendFactory.SIMILARITY.FARTHEST_NEIGHBOR_CLUSTER,
						userSimilarity);
		RecommenderBuilder recommenderBuilder = RecommendFactory
				.treeClusterRecommender(clusterSimilarity, 10);

		RecommendFactory.evaluate(
				RecommendFactory.EVALUATOR.AVERAGE_ABSOLUTE_DIFFERENCE,
				recommenderBuilder, null, dataModel, 0.7);
		RecommendFactory.statsEvaluator(recommenderBuilder, null, dataModel, 2);

		LongPrimitiveIterator iter = dataModel.getUserIDs();
		while (iter.hasNext()) {
			long uid = iter.nextLong();
			List<RecommendedItem> list = recommenderBuilder.buildRecommender(
					dataModel).recommend(uid, RECOMMENDER_NUM);
			RecommendFactory.showItems(uid, list, true);
		}
	}
}
