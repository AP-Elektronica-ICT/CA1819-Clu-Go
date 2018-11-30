import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class LocationsService {
  private baseUrl = "https://cluego.azurewebsites.net/api/location"


  constructor(private http:HttpClient) { }

  getLocations() {
    return this.http.get<Location[]>(this.baseUrl);

  }
}

export interface Location{
  locName: string 
  locLat: string ;
  locLong: string;
  locDesc: string;
}