import {Injectable} from '@angular/core';
import {TestSection} from "../../core/models/test-section";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LectureTestSectionService {

  private baseUrl = "http://localhost:8080/api/lecture/test";

  testSections: TestSection[] = [];

  private sectionUpdatedSource = new Subject<void>();
  sectionUpdated$ = this.sectionUpdatedSource.asObservable();


  constructor(private httpClient: HttpClient) {
  }

  getTestSectionByTestAndType(id: number, type: string): Observable<TestSection[]> {

    const params = new HttpParams().set('type', type)

    return this.httpClient.get<TestSection[]>(`${this.baseUrl}/${id}/section`, {params: params});
  }

  createTestSection(id: number, testSection: TestSection): Observable<Object> {
    return this.httpClient.post(`${this.baseUrl}/${id}/section`, testSection);
  }

  getTestSectionById(id: number): Observable<TestSection> {
    return this.httpClient.get<TestSection>(`${this.baseUrl}/section/${id}`);
  }

  updateTestSection(id: number, testSection: TestSection): Observable<Object> {
    return this.httpClient.put(`${this.baseUrl}/section/${id}`, testSection);
  }

  deleteTestSection(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseUrl}/section/${id}`);
  }

  announceSectionUpdated() {
    this.sectionUpdatedSource.next();
  }
}
