# Data Model


The Graph data model can be extended by adding Entities and Relationship definitions into the initial Loader.

There are 2 files:

 * `src/main/resources/LoadableGraphSpec.json`
 * `src/main/resources/RelationshipGraphSpec.json`


## Loadable Graph Spec

Used to create an 'Entity' - 1st level Object in the Graph.

`id` - The ID of the entity, used internally - can retrieve meta-data based on this
`urn_prefix`:  The urn prefix to place in front of any record createda
`relative_load_priority`: Of the list of Entities created, when should it be created?  Can be important when this entity references others as Attributes
`label`: The label for the entity - string name 
`data_location`: Where to load the data from class location or URL
`data_format`:  The loading format of the data , usually CSV
`temporal`: Whether the entity exists over time, effectiveFrom, effectiveTo
`schema_ref`: Which schema defines the Object (JSON Schema)


### Example - Country Specification
```
{
  "id": "country",
  "urn_prefix": "urn:iso:std:iso:3166:",
  "relative_load_priority": 1,
  "label": "country",
  "data_location": "/locations/iso-3166-countries.csv",
  "data_format": "text/csv",
  "temporal": true,
  "schema_ref": "./schemas/Country.json"
}

```

### Example records:

Loaded into the graph with label = 'country' an id and the meta-data below.

#### Country
```
id,                       name,       shortName,    effectiveFromDate,    effectiveToDate
urn:iso:std:iso:3166:AU,  Australia,  AU            ,1901-01-01           ,
```

#### Province

Loaded into the graph with label = 'province' an id and the meta-data below.

```
id,name,shortName,abbreviation,category
urn:iso:std:iso:3166:-2:AU-NSW,New South Wales,AU-NSW,NSW,state
```


### Relationship Graph Specification

The Relationship Graph specification is to assist in loading relationships (and attributes of the relationship)
between entities, that should already have been loaded.

`id` - identifier for the relationship
`relative_load_priority` : When to process the relationship record relative to others in the list
`subject_identifier`:  The ID to be used to find the subject in the graph
`object_identifier`: The ID to be used to find the object in the graph (Subject --> Object)
`label`: Label to be used to label the relationship, for node expansion
`temporal`: Whether the relationship definition should be a temporal one
`data_location`: Location of the source data file
`data_format`: Mime type of data, usually `text/csv`



```
{
  "id": "urn:aec.gov.au:relationships:countries_to_provinces",
  "relative_load_priority": 4,
  "subject_identifier": "country_id",
  "object_identifier": "province_id",
  "label": "has_province",
  "temporal": true,
  "data_location": "/Aus-StateResourcetest.csv",
  "data_format": "text/csv"
}
```

### Example Relationship Records

Note the need for including the listed subject, object identifiers to link the entities

`province_id`, `country_id`

Creates a link between Australia to NSW from 1/1/1900

```
province_id,relation,country_id,effectiveFromDate
urn:iso:std:iso:3166:-2:AU-NSW,province-of,urn:iso:std:iso:3166:AU,1901-01-01
```





# Recipe - Create your own Item & Relationships

Let's say we want to create an Entity for Power stations.

1) Design your data structure

Look at references like [schema.org](http://schema.org), [Microformats](http://microformats.org/) or one I like [The Data Model Resource Book](https://www.oreilly.com/library/view/the-data-model/9780471353485/08_chapter_1.html)

```

```

2) Choose an Entity name, ID, URN Prefix & Label

To be able to be loaded, the data definition should be defined to include:
 
`id`
`label` (Default)
`urn prefix`


3) Create a JSON Schema for the Object

Put a new file entry in the `src/main/resources/schemas` folder


4) Add a load definition 

Add a definition entry for the Schema created in the `src/main/resources/LoadableGraphSpec.json` file.


5) Create / Reference the source data

Create a folder under `src/main/resources` for the data set.
