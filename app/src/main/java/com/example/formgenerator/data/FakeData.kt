package com.example.formgenerator.data

data class FakeData(
    val formConfigJson: String
) {
    companion object {
        const val formConfigJson =
            """
{
  "formConfig":{
    "id":1,
    "screenConfigs":[
        {
          "id":1,
          "widgetConfigs":[
              {
                "id":1,
                "type":"TEXT_FIELD",
                "dependencies":[],
                "dataPath":"firstName",
                "hint":"Enter your first name",
                "validations":[
                    {
                        "type":"REQUIRED",
                        "message":"This field can not be empty",
                        "dependencies":[]
                    }
                ]
              },
              {
                "id":2,
                "type":"TEXT_FIELD",
                "dependencies":[],
                "dataPath":"lastName",
                "hint":"Enter your last name"
              },
              {
                "id":3,
                "type":"TEXT_FIELD",
                "dependencies":[],
                "dataPath":"age",
                "hint":"Enter your age",
                "keyboardType":"Number"
              },
              {
                "id":10,
                "type":"TEXT_FIELD",
                "dependencies":[],
                "dataPath":"father_age",
                "hint":"Enter your father's age",
                "validations":[
                    {
                        "type":"DEPENDENT",
                        "message":"father's age should be more than age",
                        "dependencies":[
                            {
                                "type":"VISIBILITY",
                                "rule":{
                                "<" : [ { "var" : "age" }, { "var" : "father_age" } ]
                                }
                            }
                        ]
                    }
                ]
              }
            ],
          "dependencies":[]
        },
        {
          "id":2,
          "widgetConfigs":[
              {
                "id":4,
                "type":"TEXT_FIELD",
                "dependencies":[],
                "dataPath":"income",
                "hint":"Enter your income"
              },
              {
                "id":5,
                "type":"TEXT",
                "dependencies":[
                    {
                      "type":"VISIBILITY",
                      "rule":{
                      "<" : [ { "var" : "income" }, 1000 ]
                      }
                    }
                  ],
                "dataPath":null,
                "text":"Your income is less than 1000"
              },
              {
                "id":6,
                "type":"TEXT",
                "dependencies":[
                    {
                      "type":"VISIBILITY",
                      "rule":{
                      ">=" : [ { "var" : "income" }, 1000 ]
                      }
                    }
                  ],
                "dataPath":null,
                "text":"Your income is more than 1000"
              }
            ]
          ,
          "dependencies":[
              {
                "type":"VISIBILITY",
                "rule":{
                  "<" : [ { "var" : "age" }, 18 ]
                }
              }
            ]
        },
        {
          "id":3,
          "widgetConfigs":[
              {
                "id":7,
                "type":"TEXT",
                "dependencies":[],
                "dataPath":null,
                "text":"text 1"
              },
              {
                "id":8,
                "type":"TEXT",
                "dependencies":[],
                "dataPath":null,
                "text":"text 2"
              },
              {
                "id":9,
                "type":"TEXT",
                "dependencies":[],
                "dataPath":null,
                "text":"text 3"
              }
            ],
          "dependencies":[
              {
                "type":"VISIBILITY",
                "rule":{
                  ">=" : [ { "var" : "age" }, 18 ]
                }
              }
            ]
        }
      ]
  }
}
"""

    }
}
