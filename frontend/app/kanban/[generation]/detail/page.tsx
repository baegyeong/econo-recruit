"use client";

import { FC } from "react";
import KanbanColumnDetailCard from "@/components/kanban/column/ColumnWithBackButton.component";
import KanbanDetailApplicant from "@/components/kanban/content/DetailApplicant.component";
import Validate from "@/components/user/Validate.component";
import { KanbanCardReq } from "@/src/apis/kanban/kanban";
import KanbanDetailWork from "@/components/kanban/content/DetailWork.component";

interface KanbanBoardDetailPageProps {
  params: {
    generation: string;
  };
  searchParams: {
    applicantId: string;
    cardId: string;
    columnIndex: string;
    type: KanbanCardReq["cardType"];
  };
}

interface DetailContentProps {
  applicantId: string;
  generation: string;
  type: KanbanCardReq["cardType"];
  cardId: string;
}

const DetailContentJunction: FC<DetailContentProps> = ({
  applicantId,
  generation,
  cardId,
  type,
}) => {
  if (type === "APPLICANT") {
    return (
      <KanbanDetailApplicant
        detailId={applicantId ?? 0}
        generation={generation}
      />
    );
  }
  if (type === "WORK_CARD") {
    return <KanbanDetailWork cardId={cardId ?? 0} generation={generation} />;
  }

  return <></>;
};

const KanbanBoardDetailPage: FC<KanbanBoardDetailPageProps> = ({
  params: { generation },
  searchParams: { columnIndex, applicantId, type, cardId },
}) => {
  return (
    <main className="flex mt-8 overflow-auto pt-12 pl-12">
      <Validate />
      <KanbanColumnDetailCard
        columnIndex={+columnIndex ?? 0}
        generation={generation}
        cardId={cardId}
        applicantId={applicantId}
      />
      <DetailContentJunction
        applicantId={applicantId}
        cardId={cardId}
        generation={generation}
        type={type}
      />
    </main>
  );
};

export default KanbanBoardDetailPage;
