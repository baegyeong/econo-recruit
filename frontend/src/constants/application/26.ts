import {
  ApplicationCheckboxWithEtcType,
  ApplicationQuestion,
  ApplicationRadio,
  ApplicationRadioByTwoRank,
  ApplicationText,
  ApplicationTimeline,
} from "./type";

export const APPLICATION = [
  {
    id: 1,
    title: "프로젝트 희망 분야를 선택해주세요.",
    subtitle: "1순위, 2순위 해당 분야를 모두 선택해주세요.",
    direction: "horizontal",
    require: true,
    nodes: [
      {
        type: "radio",
        name: "field",
        require: true,
        value: ["개발자", "디자이너", "기획자"],
      } as ApplicationRadio,
      {
        type: "radioByTwoRank",
        require: true,
        subNodes: [
          {
            splitNumber: 3,
            title: "1순위",
            name: "field1",
            require: true,
            value: ["APP", "WEB", "GAME", "AI"],
          },
          {
            splitNumber: 3,
            title: "2순위",
            name: "field2",
            require: true,
            value: ["APP", "WEB", "GAME", "AI", "선택없음"],
          },
        ],
      } as ApplicationRadioByTwoRank,
    ],
  },
  {
    id: 2,
    title: "기본 인적 사항을 입력해주세요.",
    direction: "horizontal",
    require: false,
    nodes: [
      {
        type: "text",
        name: "name",
        title: "이름",
        maxLength: 5,
        require: true,
        errorMessages: "이름을 입력해주세요.",
      } as ApplicationText,
      {
        type: "text",
        name: "contacted",
        title: "연락처",
        subtitle:
          "서류가 접수되면 접수 확인 문자가 발송될 예정이니, 연락처를 정확하게 기입해 주시기 바랍니다.",
        require: true,
        replace: "cellPhoneNumber",
        validate: "cellPhoneNumber",
        errorMessages: "연락처를 입력해주세요.",
        maxLength: 13,
      } as ApplicationText,
      {
        type: "text",
        name: "classOf",
        title: "학번",
        subtitle: "(ex) 123456)",
        require: true,
        replace: "undergradeNumber",
        validate: "undergradeNumber",
        maxLength: 6,
        errorMessages: "학번을 입력해주세요.",
      } as ApplicationText,
      {
        type: "text",
        name: "registered",
        title: "학적상태 (재학 or 휴학)",
        subtitle: "'휴학'일 경우 복학 예정 시기를 기재해 주세요.",
        require: true,
        errorMessages: "학적상태를 입력해주세요.",
      } as ApplicationText,
      {
        type: "radioByTwoRank",
        title: "학년 및 학기",
        require: true,
        subtitle: "'휴학'일 경우 휴학 직전 학기를 선택해 주세요.",
        subNodes: [
          {
            splitNumber: 4,
            name: "grade",
            require: true,
            value: ["1학년", "2학년", "3학년", "4학년"],
          },
          {
            splitNumber: 2,
            name: "semester",
            require: true,
            value: ["1학기", "2학기"],
          },
        ],
      } as ApplicationRadioByTwoRank,
    ],
  },
  {
    id: 3,
    title: "기본 인적 사항을 입력해주세요.",
    subtitle: "복수 전공과 부전공은 선택 입력사항입니다.",
    direction: "horizontal",
    require: false,
    nodes: [
      {
        type: "text",
        name: "major",
        title: "전공",
        require: true,
        errorMessages: "전공을 입력해주세요.",
      } as ApplicationText,
      {
        type: "text",
        name: "doubleMajor",
        title: "복수전공",
        require: false,
      } as ApplicationText,
      {
        type: "text",
        name: "minor",
        title: "부전공",
        require: false,
      } as ApplicationText,
    ],
  },
  {
    id: 4,
    title: "기타 질문 사항에 답변해주세요.",
    direction: "horizontal",
    require: false,
    nodes: [
      {
        name: "activity",
        require: true,
        type: "text",
        title:
          "학업 외에 병행하고 있거나 향후 계획 중에 있는 활동이 있으시다면 적어주세요.",
        subtitle:
          "(동아리, 연구실, 아르바이트, 스터디, 교환학생 등), 없을 경우, 없음으로 기재해 주세요.",
      } as ApplicationText,
      {
        name: "channel",
        require: true,
        type: "checkboxWithEtc",
        title: "지원 경로(중복 선택 가능)",
        value: [
          "학과 공지사항",
          "홍보 포스터",
          "지인 소개",
          "인스타그램",
          "페이스북",
          "에브리타임",
        ],
        errorMessages: "지원 경로를 선택해주세요.",
      } as ApplicationCheckboxWithEtcType,
    ],
  },
] as ApplicationQuestion[];

export const APPLICATION_NAVBAR = [
  { id: 1, title: "프로젝트 희망 분야를 선택해주세요." },
  { id: 2, title: "기본 인적 사항을 입력해주세요." },
  { id: 4, title: "기타 질문 사항에 답변해주세요." },
];

export const APPLICATION_TIMELINE = {
  seperate: 30,
  time: [
    {
      startTime: new Date(2023, 9, 20, 10, 0, 0),
      endTime: new Date(2023, 9, 20, 21, 0, 0),
    },
    {
      startTime: new Date(2023, 9, 21, 10, 0, 0),
      endTime: new Date(2023, 9, 21, 21, 0, 0),
    },
    {
      startTime: new Date(2023, 9, 22, 10, 0, 0),
      endTime: new Date(2023, 9, 22, 21, 0, 0),
    },
  ],
  disableTime: [],
} as ApplicationTimeline;

export const END_DATE = {
  month: 3,
  date: 19,
};
