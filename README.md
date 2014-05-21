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
* Within those resource only the required fields are exposed.
* Resources are created and retrieved using user provided ids, making it
possible to track items in SeqWare using ids that originate from an
external LIMS system.

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
