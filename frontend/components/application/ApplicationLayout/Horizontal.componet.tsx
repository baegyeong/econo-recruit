"use client";

import Txt from "@/components/common/Txt.component";
import {
  ApplicationNode,
  ApplicationQuestion,
} from "@/constants/application/type";
import { FC } from "react";
import { junctinOrLayout } from "../JunctionOrLayout";

interface ApplicationHorizontalLayoutProps {
  applicationQuestion: ApplicationQuestion;
}

const ApplicationHorizontalLayout: FC<ApplicationHorizontalLayoutProps> = ({
  applicationQuestion,
}) => {
  return (
    <div className="flex gap-6 pr-12">
      <div className="flex-1">
        <div className="mb-4 flex gap-2">
          <Txt typography="h6">{`${applicationQuestion.id}. `}</Txt>
          <Txt typography="h6" className="break-keep">{`${
            applicationQuestion.title
          }${applicationQuestion.require ? "*" : ""}`}</Txt>
        </div>
        {applicationQuestion.subtitle && (
          <div className="pl-6">
            <Txt className="text-sm">{applicationQuestion.subtitle}</Txt>
          </div>
        )}
      </div>
      <div className="flex-1">
        {applicationQuestion.nodes.map((node, index) => {
          return (
            <div key={index} className="mb-4">
              {junctinOrLayout(node)}
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default ApplicationHorizontalLayout;
