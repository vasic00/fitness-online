import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ExerciseDialogComponent } from '../exercise-dialog/exercise-dialog.component';
import { ExerciseService } from '../services/exercise.service';
import { Exercise } from '../../model/Exercise';
import { Chart } from 'chart.js/auto';
import html2canvas from 'html2canvas';
import jspdf, { jsPDF } from 'jspdf';

@Component({
  selector: 'app-activity-log',
  templateUrl: './activity-log.component.html',
  styleUrl: './activity-log.component.css',
})
export class ActivityLogComponent implements OnInit {


  showGraphs: boolean = true;
  yearMonth: string = "";

  cards: Exercise[] = [];

  months: string[] = [];
  exercises: Exercise[] = [];
  public chart: any;
  public chart2: any;
  public chart3: any;
  public chart4: any;

  public selectedPeriod: string = "One month";

  @ViewChild("chart1") chartElementRef1!: ElementRef;
  @ViewChild("chart2") chartElementRef2!: ElementRef;
  @ViewChild("chart3") chartElementRef3!: ElementRef;
  @ViewChild("chart4") chartElementRef4!: ElementRef;
  @ViewChild("contentToConvert") contentToConvert!: ElementRef;

  constructor(private dialog: MatDialog,
    private exerciseService: ExerciseService) {
  }

  ngOnInit() {
    // let date: Date = new Date();
    // this.yearMonth = date.getFullYear() + "/" + date.getMonth();
    let date: Date = new Date();
    this.yearMonth = date.getFullYear() + "/" + (date.getMonth() + 1);
    console.log("YEAR MONTH: " + this.yearMonth);
    let counter: number = 0;
    while (counter < 12) {
      let monthNumber: number = date.getMonth();
      this.months.push(date.getFullYear() + "/" + (monthNumber + 1));
      if (monthNumber === 1) {
        date.setFullYear(date.getFullYear() - 1);
        date.setMonth(12);
      }
      else date.setMonth(monthNumber - 1);
      counter++;
    }
    this.exerciseService.getAllByUser().subscribe((data: any) => {
      console.log("Got all exercises!");
      this.exercises = data as Exercise[];
      this.updateCheck();
    })
  }

  updateCheck() {
    if (this.showGraphs)
      this.updateChartOptions();
    else this.updateCards();
  }

  switch() {
    this.showGraphs = !this.showGraphs;
    this.updateCheck();
  }

  newExercise() {
    console.log("KLIKNUTO NA NEW EXERCISE");
    let dialogRef = this.dialog.open(ExerciseDialogComponent, {
      maxWidth: "80vw",
    });
    dialogRef.afterClosed().subscribe((data: any) => {
      this.exerciseService.add(data as Exercise).subscribe((data: any) => {
        console.log("Successfully added exercise!")
        this.exercises.push(data as Exercise);
        this.updateCheck();
      });
    })
  }

  updateCards() {
    this.cards = this.exercises.filter((exercise) => {
      // console.log(exercise.date.split("/")[0] + " --- " + this.yearMonth.split("/")[0]);
      let dateMonth: string = exercise.date.split("-")[1];
      if (dateMonth.startsWith("0"))
        dateMonth = dateMonth.replace("0", "");
      // console.log(dateMonth + " --- " + this.yearMonth.split("/")[1]);
      return exercise.date.split("-")[0] === this.yearMonth.split("/")[0] && dateMonth === this.yearMonth.split("/")[1];
    });
  }

  updateChartOptions() {
    let monthlyExercises = this.getMonthlyDataPoints();
    let yAxis: string[] = [];
    let xAxis: number[] = [];
    let xAxisIntensity: number[] = [];
    let xAxisDuration: number[] = [];
    console.log("INSIDE UPDATECHARTOPTIONS: ");
    monthlyExercises.forEach((ex) => {
      console.log(ex);
      yAxis.push(ex.date.split("-")[1] + "-" + ex.date.split("-")[2])
      xAxis.push(ex.result);
      xAxisIntensity.push(ex.intensity);
      xAxisDuration.push(ex.duration);
    })
    if (this.chart === null || this.chart === undefined) {
      let context1 = this.chartElementRef1.nativeElement;
      this.chart = new Chart(context1, {
        type: 'line', //this denotes tha type of chart

        data: {// values on X-Axis
          labels: yAxis,
          datasets: [
            {
              label: "Result",
              data: xAxis,
              backgroundColor: '#3f51b5',
              pointRadius: 5,
            },
          ]
        },
        options: {
          aspectRatio: 1 | 2,
          responsive: true,
          maintainAspectRatio: false,
          scales: {
            y: {
              min: 40
            }
          },
          plugins: {
            title: {
              display: true,
              text: 'Weight loss over time',
              font: {
                size: 20,
              }
            }
          }
        }
      })
    }
    else {
      this.chart.data.datasets = [
        {
          label: "Result",
          data: xAxis,
          backgroundColor: '#3f51b5'
        },
      ]
      this.chart.data.labels = yAxis;
      this.chart.update();
    }
    if (this.chart2 === null || this.chart2 === undefined) {
      let context2 = this.chartElementRef2.nativeElement;
      this.chart2 = new Chart(context2, {
        type: 'bar', //this denotes the type of chart

        data: {// values on X-Axis
          labels: yAxis,
          datasets: [
            {
              label: "Result",
              data: xAxis,
              backgroundColor: '#3f51b5'
            },
            {
              label: "Intensity",
              data: xAxisIntensity,
              backgroundColor: '#8b59b4'
            },
          ]
        },
        options: {
          aspectRatio: 1 | 2,
          responsive: true,
          maintainAspectRatio: false,
          scales: {
            y: {
              min: 40
            }
          },
          plugins: {
            title: {
              display: true,
              text: 'Weight and intensity',
              font: {
                size: 20,
              }
            }
          }
        }
      })
    }
    else {
      this.chart2.data.datasets = [
        {
          label: "Result",
          data: xAxis,
          backgroundColor: '#3f51b5'
        },
        {
          label: "Intensity",
          data: xAxisIntensity,
          backgroundColor: '#8b59b4'
        },
      ]
      this.chart2.data.labels = yAxis;
      this.chart2.update();
    }
    if (this.chart3 === null || this.chart3 === undefined) {
      let context3 = this.chartElementRef3.nativeElement;
      this.chart3 = new Chart(context3, {
        type: 'bar', //this denotes the type of chart

        data: {// values on X-Axis
          labels: yAxis,
          datasets: [
            {
              label: "Result",
              data: xAxis,
              backgroundColor: '#3f51b5'
            },
            {
              label: "Duration",
              data: xAxisDuration,
              backgroundColor: 'pink'
            },
          ]
        },
        options: {
          aspectRatio: 1 | 2,
          responsive: true,
          maintainAspectRatio: false,
          scales: {
            y: {
              min: 0
            }
          },
          plugins: {
            title: {
              display: true,
              text: 'Weight and duration',
              font: {
                size: 20,
              }
            }
          }
        }
      })
    }
    else {
      this.chart3.data.datasets = [
        {
          label: "Result",
          data: xAxis,
          backgroundColor: '#3f51b5'
        },
        {
          label: "Duration",
          data: xAxisDuration,
          backgroundColor: 'pink'
        },
      ]
      this.chart3.data.labels = yAxis;
      this.chart3.update();
    }
    if (this.chart4 === null || this.chart4 === undefined) {
      let context4 = this.chartElementRef4.nativeElement;
      this.chart4 = new Chart(context4, {
        type: 'bar', //this denotes tha type of chart

        data: {// values on X-Axis
          labels: yAxis,
          datasets: [
            {
              label: "Result",
              data: xAxis,
              backgroundColor: '#3f51b5'
            },
            {
              label: "Intensity",
              data: xAxisIntensity,
              backgroundColor: '#8b59b4'
            },
            {
              label: "Duration",
              data: xAxisDuration,
              backgroundColor: 'pink'
            },
          ]
        },
        options: {
          aspectRatio: 1 | 2,
          responsive: true,
          maintainAspectRatio: false,
          scales: {
            y: {
              min: 0
            }
          },
          plugins: {
            title: {
              display: true,
              text: 'Weight, intensity and duration',
              font: {
                size: 20,
              }
            }
          }
        }
      })
    }
    else {
      this.chart4.data.datasets = [
        {
          label: "Result",
          data: xAxis,
          backgroundColor: '#3f51b5'
        },
        {
          label: "Intensity",
          data: xAxisIntensity,
          backgroundColor: '#8b59b4'
        },
        {
          label: "Duration",
          data: xAxisDuration,
          backgroundColor: 'pink'
        },
      ]
      this.chart4.data.labels = yAxis;
      this.chart4.update();
    }
  }

  private getMonthlyDataPoints(): Exercise[] {
    let period: number = 0;
    if (this.selectedPeriod === "One month")
      period = 30;
    else if (this.selectedPeriod === "Six months")
      period = 180;
    let currentDate: Date = new Date();
    let monthlyExercises = this.exercises.filter((exercise) => {
      let differenceInMilliseconds = currentDate.getTime() - new Date(exercise.date).getTime();
      console.log("Broj dana: " + Math.floor(differenceInMilliseconds / (1000 * 60 * 60 * 24)));
      return Math.floor(differenceInMilliseconds / (1000 * 60 * 60 * 24)) <= period;
    })
    let map = new Map();
    monthlyExercises.forEach((exercise) => {
      let obj = map.get(exercise.date);
      if (obj !== undefined) {
        obj.count += 1;
        obj.sumIntensity += exercise.intensity;
        obj.sumDuration += exercise.duration;
        if (exercise.result < obj.result)
          obj.result = exercise.result;
        map.set(exercise.date, obj);
      }
      else {
        map.set(exercise.date, { count: 1, sumIntensity: exercise.intensity, sumDuration: exercise.duration, result: exercise.result });
      }
    });
    monthlyExercises = [];
    map.forEach((value, key) => {
      let exercise: Exercise = new Exercise();
      exercise.date = key;
      exercise.duration = value.sumDuration;
      exercise.intensity = value.sumIntensity / value.count;
      exercise.result = value.result;
      monthlyExercises.push(exercise);
    })
    monthlyExercises.sort((e1: Exercise, e2: Exercise) => new Date(e1.date).getTime() - new Date(e2.date).getTime());
    return monthlyExercises;
  }

  public convertToPDF() {
    const doc = new jsPDF();
    const element = document.getElementById("contentToConvert");
    let elementPreWidth = element!.style.width;
    let elementPreHeight = element!.style.height;
    element!.style.width = "1620px";
    element!.style.height = "2150px";


    setTimeout(() => {
      html2canvas(element!).then(canvas => {
        const imgData = canvas.toDataURL('image/png');
        const imgWidth = 210;
        const imgHeight = (canvas.height * imgWidth) / canvas.width;
        doc.addImage(imgData, 'PNG', 0, 0, imgWidth, imgHeight);
        doc.save(new Date().toLocaleString() + " exercises.pdf");
        element!.style.width = elementPreWidth;
        element!.style.height = elementPreHeight;
      });
    }, 200);
  }
}
