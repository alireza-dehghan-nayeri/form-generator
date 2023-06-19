package com.example.formgenerator.data

data class FakeData(
    val formConfigJson: String
) {
    companion object {
        const val formValueJsonCancer =
            """
                {
  "height": "165",
  "physicallyActive": "No",
  "usedHormones": "No",
  "breastCancerHistoryInFamily": "Yes",
  "hasDenseBreast": "No",
  "hadRadiationTherapy": "No",
  "weight": "65",
  "firstName": "Alireza",
  "exposedToDES": "No",
  "phoneNumber": "09156257687",
  "diagnosedBefore": "No",
  "drinker": "No",
  "lastName": "Dehghan",
  "gender": "Female",
  "diagnosedRelatives":"mother"
}
            """

        const val formConfigJsonCancer =
            """
                {
  "formConfig": {
    "id": 1,
    "screenConfigs": [
      {
        "id": 1,
        "title": "Personal Information",
        "widgetConfigs": [
          {
            "id": 1,
            "type": "TEXT_FIELD",
            "dataPath": "firstName",
            "validations": [
              {
                "type": "REQUIRED",
                "message": "This field can not be empty",
                "dependencies": []
              }
            ],
            "dependencies": [],
            "hint": "Enter your first name",
            "keyboardType": "Text"
          },
          {
            "id": 2,
            "type": "TEXT_FIELD",
            "dataPath": "lastName",
            "validations": [
              {
                "type": "REQUIRED",
                "message": "This field can not be empty",
                "dependencies": []
              }
            ],
            "dependencies": [],
            "hint": "Enter Your last name",
            "keyboardType": "Text"
          },
          {
            "id": 3,
            "type": "TEXT_FIELD",
            "dataPath": "phoneNumber",
            "validations": [
              {
                "type": "REQUIRED",
                "message": "This field can not be empty",
                "dependencies": []
              }
            ],
            "dependencies": [],
            "hint": "Enter your phone number",
            "keyboardType": "Phone"
          },
          {
            "id": 4,
            "type": "DROP_DOWN_MENU",
            "dataPath": "gender",
            "validations": [
              {
                "type": "REQUIRED",
                "message": "Please choose an option",
                "dependencies": []
              }
            ],
            "dependencies": [],
            "text": "Select your gender",
            "options": [
              "Male",
              "Female"
            ]
          }
        ],
        "dependencies": []
      },
      {
        "id": 2,
        "title": "Physical Condition",
        "widgetConfigs": [
          {
            "id": 5,
            "type": "TEXT_FIELD",
            "dataPath": "weight",
            "validations": [
              {
                "type": "REQUIRED",
                "message": "This field can not be empty",
                "dependencies": []
              }
            ],
            "dependencies": [],
            "hint": "Enter your weight",
            "keyboardType": "Number"
          },
          {
            "id": 6,
            "type": "TEXT_FIELD",
            "dataPath": "height",
            "validations": [
              {
                "type": "REQUIRED",
                "message": "This field can not be empty",
                "dependencies": []
              }
            ],
            "dependencies": [],
            "hint": "Enter your height",
            "keyboardType": "Number"
          },
          {
            "id": 7,
            "type": "RADIO_GROUP",
            "dataPath": "physicallyActive",
            "validations": [
              {
                "type": "REQUIRED",
                "message": "Please choose an item",
                "dependencies": []
              }
            ],
            "dependencies": [],
            "text": "Are you physically active?",
            "items": [
              "Yes",
              "No"
            ]
          },
          {
            "id": 8,
            "type": "RADIO_GROUP",
            "dataPath": "hasDenseBreast",
            "validations": [],
            "dependencies": [],
            "text": "Do you have dense breast?",
            "items": [
              "Yes",
              "No"
            ]
          }
        ],
        "dependencies": []
      },
      {
        "id": 3,
        "title": "Reproductive History",
        "widgetConfigs": [
          {
            "id": 9,
            "type": "TEXT_FIELD",
            "dataPath": "menstrualPeriodAge",
            "validations": [
              {
                "type": "REQUIRED",
                "message": "This field can not be empty",
                "dependencies": []
              }
            ],
            "dependencies": [],
            "hint": "Enter your age of first menstrual periods",
            "keyboardType": "Number"
          },
          {
            "id": 10,
            "type": "RADIO_GROUP",
            "dataPath": "beenPregnant",
            "validations": [],
            "dependencies": [],
            "text": "Have you ever been pregnant?",
            "items": [
              "Yes",
              "No"
            ]
          },
          {
            "id": 11,
            "type": "TEXT_FIELD",
            "dataPath": "pregnancyAge",
            "validations": [
              {
                "type": "REQUIRED",
                "message": "This field can not be empty",
                "dependencies": []
              }
            ],
            "dependencies": [
                {
                "type":"VISIBILITY",
                "rule":{
                  "==" : [ { "var" : "beenPregnant" }, "Yes" ]
                }
            }
            ],
            "hint": "Enter your age of first pregnancy",
            "keyboardType": "Number"
          },
          {
            "id": 12,
            "type": "RADIO_GROUP",
            "dataPath": "menopause",
            "validations": [],
            "dependencies": [],
            "text": "Are you menopause?",
            "items": [
              "Yes",
              "No"
            ]
          },
          {
            "id": 13,
            "type": "TEXT_FIELD",
            "dataPath": "menopauseAge",
            "validations": [
              {
                "type": "REQUIRED",
                "message": "This field can not be empty",
                "dependencies": []
              }
            ],
            "dependencies": [
                {
                "type":"VISIBILITY",
                "rule":{
                  "==" : [ { "var" : "menopause" }, "Yes" ]
                }
            }
            ],
            "hint": "Enter your age of menopause",
            "keyboardType": "Number"
          }
        ],
        "dependencies": [
            {
                "type":"VISIBILITY",
                "rule":{
                  "==" : [ { "var" : "gender" }, "Female" ]
                }
            }
        ]
      },
      {
        "id": 4,
        "title": "Health",
        "widgetConfigs": [
          {
            "id": 14,
            "type": "RADIO_GROUP",
            "dataPath": "drinker",
            "validations": [],
            "dependencies": [],
            "text": "Are you a drinker?",
            "items": [
              "Yes",
              "No"
            ]
          },
          {
            "id": 15,
            "type": "RADIO_GROUP",
            "dataPath": "diagnosedBefore",
            "validations": [],
            "dependencies": [],
            "text": "Have you been diagnosed before?",
            "items": [
              "Yes",
              "No"
            ]
          },
          {
            "id": 16,
            "type": "RADIO_GROUP",
            "dataPath": "exposedToDES",
            "validations": [],
            "dependencies": [],
            "text": "have you or your mother taken DES drug?",
            "items": [
              "Yes",
              "No"
            ]
          },
          {
            "id": 17,
            "type": "RADIO_GROUP",
            "dataPath": "usedHormones",
            "validations": [],
            "dependencies": [],
            "text": "Have you used hormon drugs?",
            "items": [
              "Yes",
              "No"
            ]
          },
          {
            "id": 18,
            "type": "RADIO_GROUP",
            "dataPath": "hadRadiationTherapy",
            "validations": [],
            "dependencies": [],
            "text": "Have you done radiation therapy?",
            "items": [
              "Yes",
              "No"
            ]
          }
        ],
        "dependencies": []
      },
      {
        "id": 5,
        "title": "Family History",
        "widgetConfigs": [
          {
            "id": 19,
            "type": "RADIO_GROUP",
            "dataPath": "breastCancerHistoryInFamily",
            "validations": [],
            "dependencies": [],
            "text": "Have any members of your family diagnosed with breast cancer?",
            "items": [
              "Yes",
              "No"
            ]
          },
          {
            "id": 20,
            "type": "TEXT_FIELD",
            "dataPath": "diagnosedRelatives",
            "validations": [
              {
                "type": "REQUIRED",
                "message": "This field can not be empty",
                "dependencies": []
              }
            ],
            "dependencies": [
                {
                "type":"VISIBILITY",
                "rule":{
                  "==" : [ { "var" : "breastCancerHistoryInFamily" }, "Yes" ]
                }
            }
            ],
            "hint": "Enter your relation with that member",
            "keyboardType": "Text"
          }
        ],
        "dependencies": []
      }
    ]
  }
}
            """

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
                        "message":"Father's age should be more than age",
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
