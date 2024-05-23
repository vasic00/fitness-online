import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../config/environment';
import { Program } from '../../model/Program';

@Injectable({
  providedIn: 'root'
})
export class ProgramService {

  constructor(private httpClient: HttpClient) { }

  getCategories(): any {
    return this.httpClient.get(
      `${environment.baseURL}${environment.categoriesPath}`
    );
  }

  getPrograms(index: number, size: number): any {
    return this.httpClient.get(`${environment.baseURL}${environment.programsPath}` + '/active' + '/-1' + '/-1'
      + '/false' + '/-' + '/-' + '/-1' + '?pageNo=' + index + '&pageSize=' + size);
  }

  getFilteredPrograms(price1: number, price2: number, valid: string, programName: string, categoryName: string, difficultyLevel: number,
    index: number, size: number): any {
    return this.httpClient.get(`${environment.baseURL}${environment.programsPath}` + '/active' + '/' + price1 + '/' + price2 + '/'
      + valid + '/' + programName + '/' + categoryName + '/' + difficultyLevel + '?pageNo=' + index + '&pageSize=' + size);
  }

  getProgramsForCreator(index: number, size: number): any {
    return this.httpClient.get(`${environment.baseURL}${environment.programsPath}` + '/creator' + '/-1' + '/-1'
      + '/-' + '/-' + '/-' + '/-1' + '?pageNo=' + index + '&pageSize=' + size);
  }

  getFilteredProgramsForCreator(price1: number, price2: number, valid: string, programName: string, categoryName: string, difficultyLevel: number,
    index: number, size: number): any {
    return this.httpClient.get(`${environment.baseURL}${environment.programsPath}` + '/creator' + '/' + price1 + '/' + price2 + '/'
      + valid + '/' + programName + '/' + categoryName + '/' + difficultyLevel + '?pageNo=' + index + '&pageSize=' + size);
  }

  endsIn(endDate: number): string {
    let result = endDate - new Date().getTime();
    let days = Math.floor(result / (1000 * 60 * 60 * 24));
    let hours = Math.floor((result % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    // let minutes = Math.floor((result % (1000 * 60 * 60)) / (1000 * 60));
    return days + 'd ' + hours + 'h ';
  }

  checkIfParticipated(id: number): any {
    return this.httpClient.get(`${environment.baseURL}${environment.programsPath}` + '/participated/' + id);
  }

  delete(id: number): any {
    return this.httpClient.delete(`${environment.baseURL}${environment.programsPath}` + '/' + id);
  }

  addProgram(program: Program, rand: string): any {
    return this.httpClient.post(`${environment.baseURL}${environment.programsPath}` + '/' + rand, program);
  }

  uploadImageForProgram(imageFile: File, rand: string): any {
    const formData = new FormData();
    formData.append('image', imageFile);
    let params = new HttpParams().set('id', rand);
    return this.httpClient.post(`${environment.baseURL}${environment.imagesPath}`, formData, { params: params });
  }

  getParticipations(): any {
    return this.httpClient.get(`${environment.baseURL}${environment.programsPath}` + '/participations');
  }
}
