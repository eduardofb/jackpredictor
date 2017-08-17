package org.template.classification

import org.apache.predictionio.controller.PDataSource
import org.apache.predictionio.controller.EmptyEvaluationInfo
import org.apache.predictionio.controller.EmptyActualResult
import org.apache.predictionio.controller.Params
import org.apache.predictionio.data.storage.Event
import org.apache.predictionio.data.storage.Storage

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.Vectors

import grizzled.slf4j.Logger

case class DataSourceParams(appId: Int) extends Params

class DataSource(val dsp: DataSourceParams)
  extends PDataSource[TrainingData,
      EmptyEvaluationInfo, Query, EmptyActualResult] {

  @transient lazy val logger = Logger[this.type]

  override
  def readTraining(sc: SparkContext): TrainingData = {
    val eventsDb = Storage.getPEvents()
    val labeledPoints: RDD[LabeledPoint] = eventsDb.aggregateProperties(
      appId = dsp.appId,
      entityType = "survivor",
      // only keep entities with these required properties defined
      required = Some(List("target", "class", "sex", "age")))(sc)
      // aggregateProperties() returns RDD pair of
      // entity ID and its aggregated properties
      .map { case (entityId, properties) =>
        try {
          LabeledPoint(properties.get[Double]("target"),
            Vectors.dense(Array(
              properties.get[Double]("class"),
              properties.get[Double]("sex"),
              properties.get[Double]("age")
            ))
          )
        } catch {
          case e: Exception => {
            logger.error(s"Failed to get properties ${properties} of" +
              s" ${entityId}. Exception: ${e}.")
            throw e
          }
        }
      }.cache()

    new TrainingData(labeledPoints)
  }
}

class TrainingData(
  val labeledPoints: RDD[LabeledPoint]
) extends Serializable
