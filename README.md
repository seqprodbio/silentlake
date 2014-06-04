## Introduction

Silentlake makes synchronizing data with [SeqWare](http://seqware.github.com)
easy.

Silentlake is a webservice that reads and writes to the 
SeqWare database and simplifies the process of
synchronizing sequencer run and sample data between a LIMS (Labratory 
Information Management System) and SeqWare by exposing an API that only
includes the required resources and fields necessary to successfully 
populate SeqWare with the data it requires to function.

## Features

* Only resources required to populate the SeqWare database are exposed.
* Within those resources only the required fields are exposed.
* Resources are created and retrieved using user provided ids, making it
possible to track items in SeqWare using ids that originate from an
external LIMS system.

## Using

Silentlake is under development, so these are currently the only
instructions provided for its use.

### Configure Database Connection

A SeqWare 1.x database must be available to use Silentlake. Configure the
connection in the root pom.xml file via the following settings.
   
      <seqware_meta_db_name>seqware_meta_db</seqware_meta_db_name>
      <seqware_meta_db_port>5432</seqware_meta_db_port>
      <seqware_meta_db_password>secret</seqware_meta_db_password>
      <seqware_meta_db_user>username</seqware_meta_db_user>
      <seqware_meta_db_host>localhost</seqware_meta_db_host>

### Build

      mvn install

### Run

     cd silentlake-ws
     mvn embedded-glassfish:run

### Use

    # Add a user
    curl -X PUT -H "Content-Type:application/json" -d '{ "email": "first.last@oicr.on.ca", "institution": "OICR", "firstname": "first", "lastname": "last" }' http://localhost:38080/seqware-admin-webservice/webresources/user/42
    # Retrieve user
    curl -X GET http://localhost:38080/seqware-admin-webservice/webresources/user/42
    # Retrieve all users
    curl -X GET http://localhost:38080/seqware-admin-webservice/webresources/users
    # Add a study
    curl -X PUT -H "Content-Type:application/json" -d '{ "title": "HeterogeneityinBC", "description": "Heterogeneity in Breast Cancer", "institution": "Ontario Institute for Cancer Research", "institution_project_name": "HiBC", "type_id": 11, "owner_id": 42 }' http://localhost:38080/seqware-admin-webservice/webresources/study/120
    # Retrieve a study
    curl -X GET http://localhost:38080/seqware-admin-webservice/webresources/study/120
    # Retrieve all studies
    curl -X GET http://localhost:38080/seqware-admin-webservice/webresources/studies

    # (Optional) Pretty print output if python is installed
    curl -X GET http://localhost:38080/seqware-admin-webservice/webresources/studies | python -mjson.tool

During development consider using the browser plugin Postman to manage and run queries.
To see how to use more features, see the [usages.md](https://github.com/seqprodbio/silentlake/blob/master/usages.md) file.

## License

    Silentlake is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Silentlake is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Silentlake.  If not, see <http://www.gnu.org/licenses/>.
