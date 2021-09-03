package com.example.demo;

import static org.apache.spark.sql.functions.col;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import scala.Tuple2;

@RunWith(SpringJUnit4ClassRunner.class)
public class test {

	@Test
	public void test1() {
		SparkConf sparkConf = new SparkConf();
		sparkConf.setAppName("Spark_GroupByKey_Sample");
		sparkConf.setMaster("local");
		JavaSparkContext context = new JavaSparkContext(sparkConf);
		List<User> data = getTestData();
		JavaRDD<User> distData = context.parallelize(data);
		// FirstRDD
		JavaPairRDD<String, User> firstRDD = distData.mapToPair(num -> new Tuple2<>(num.getName(), num));
		JavaPairRDD<String, Iterable<User>> firstRDD2 = firstRDD.groupByKey();
		firstRDD2.foreach(x -> System.out.println(x));
		JavaPairRDD<String, User> firstRDD3 = aggregateByDeviceId(firstRDD);
		firstRDD3.foreach(x -> System.out.println(x));
		context.close();
	}

	private static JavaPairRDD<String, User> aggregateByDeviceId(JavaPairRDD<String, User> users) {
		// Function2的前两个accessLogInfo对应call的前两个，第三个是返回的
		return users.reduceByKey((user1, user2) -> {
			user1.setAge(user1.getAge() + user2.getAge());
			user1.setTotal(user1.getTotal() + user2.getTotal());
			return user1;
		});
	}

	private static List<User> getTestData() {
		List<User> list = new ArrayList<User>();
		User user1 = new User("1", "tom", "M", 21);
		User user2 = new User("2", "tom", "M", 22);
		User user3 = new User("3", "cat", "M", 23);
		User user4 = new User("4", "cat", "M", 24);
		User user5 = new User("4", "cat", "M", 24);
		list.add(user1);
		list.add(user2);
		list.add(user3);
		list.add(user4);
		list.add(user5);
		return list;
	}

	@Test
	public void readJSONProgrammatic() {
		SparkSession spark = SparkSession.builder().appName("SparkSQL_demo").master("local").getOrCreate();
		List<User> data = getTestData();
		Encoder<User> personEncoder = Encoders.bean(User.class);
		Dataset<User> pedataset = spark.createDataset(data, personEncoder);
		JavaRDD<User> peRdd = pedataset.toJavaRDD();
		String schemaString = "name age";
		List<StructField> fields = new ArrayList<StructField>(16);
		for (String fieldName : schemaString.split(" ")) {
			StructField field = DataTypes.createStructField(fieldName, DataTypes.StringType, true);
			fields.add(field);
		}
		StructType schema = DataTypes.createStructType(fields);
		JavaRDD<Row> rowRDD = peRdd.map(record -> {
			String[] attributes = "name,age,id".split(",");
			return RowFactory.create(attributes[0], attributes[1].trim());
		});
		spark.createDataFrame(rowRDD, schema);
	}

	@Test
	public void readJsonInferSchema() {
		SparkSession spark = SparkSession.builder().appName("SparkSQL_demo").master("local").getOrCreate();
		List<User> data = getTestData();
		Encoder<User> personEncoder = Encoders.bean(User.class);
		Dataset<User> pedataset = spark.createDataset(data, personEncoder);
		JavaRDD<User> peRdd = pedataset.toJavaRDD();
		Dataset<Row> personDF = spark.createDataFrame(peRdd, User.class);
		personDF.show();
		personDF.createOrReplaceTempView("per");
		Dataset<Row> dataset = spark.sql("select name from per where age >21");
		dataset.as(personEncoder).toJavaRDD().collect();
	}

	/**
	 * 读取json文件为 Dataset
	 *
	 * @param sparkSession
	 * @throws AnalysisException
	 */
	@Test
	public void readJsonRunBasicDataFrame() throws AnalysisException {
		SparkSession spark = SparkSession.builder().appName("SparkSQL_demo").master("local").getOrCreate();
		List<User> data = getTestData();
		Encoder<User> personEncoder = Encoders.bean(User.class);
		spark.createDataset(data, personEncoder);
		Dataset<Row> df = spark.createDataFrame(data, User.class);
		df.show();
		df.printSchema();
		df.select(col("name"), col("age").plus(1)).show();
		df.filter(col("age").gt(21)).show();
		df.groupBy("age").count().show();
		df.createOrReplaceTempView("per_temp");
		Dataset<Row> sql = spark.sql("select * from per_temp");
		sql.show();
		df.createGlobalTempView("per_glo");
		spark.sql("SELECT * FROM global_temp.per_glo").show();
	}

	@Test
	public void readJsonRunBasicDataSet() {
		SparkSession spark = SparkSession.builder().appName("SparkSQL_demo").master("local").getOrCreate();
		List<User> data = getTestData();
		Encoder<User> personEncoder = Encoders.bean(User.class);
		Dataset<User> javaBeansDS = spark.createDataset(data, personEncoder);
		javaBeansDS.show();
		Dataset<User> df = spark.createDataset(data, personEncoder);
		df.show();
	}

	/**
	 * 测试sql函数
	 *
	 * @param sparkSession
	 */
	@Test
	public void sparkTest() {
		SparkSession sparkSession = SparkSession.builder().appName("SparkSQL_demo").master("local").getOrCreate();
		Encoder<User> personEncoder = Encoders.bean(User.class);
		List<User> data = getTestData();
		Dataset<User> df = sparkSession.createDataset(data, personEncoder);
		df.createOrReplaceTempView("per_fun");
		sparkSession.sql("select * from per_fun").show();
		sparkSession.sql("select name, count(id) as total from per_fun group by name").show();
	}

}
