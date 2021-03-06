package revertedindex;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import wordcount.WordCountCommonJob;
import wordcount.WordCountCommonMapper;
import wordcount.WordCountCommonReducer;
import wordcount.WordCountComparator;


/**
 * <p>Title:RevertedIndexMain</p>
 * <p>Description: </p>
 *
 * @author zhuxl
 * @version v1.0
 * @date 2019/7/3 15:39
 */
public class RevertedIndexMain {
    public static void main(String[] args) throws Exception {

        // 1. 创建job
        Job job = Job.getInstance(new Configuration());
        job.setJarByClass(RevertedIndexMain.class);

        // 2. 设置mapper及其输出类型
        job.setMapperClass(RevertedIndexMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);


        /*添加combiner*/
        job.setCombinerClass(RevertedIndexCombiner.class);

        // 3. 设置reduce及其输出类型
        job.setReducerClass(RevertedIndexReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // 4. 设置输入输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 5. 运行job
        job.waitForCompletion(true);

    }
}
